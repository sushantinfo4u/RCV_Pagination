package com.task.leap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.provider.ContactsContract
import android.util.Log
import android.widget.ProgressBar


class MainActivity : AppCompatActivity() {

    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var firstVisibleItem: Int = 0
    private var previousTotal = 0
    private var visibleThreshold = 5

    val layoutManagerObj = LinearLayoutManager(this)

    val contactList: ArrayList<ContactData> = ArrayList()
    var rv_Contactlist: RecyclerView? = null
    var progress: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContactData()
        setrecycleView()

    }

    private fun setContactData() {

        val cr = contentResolver
        val cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        if (cur?.count ?: 0 > 0) {
            while (cur != null && cur.moveToNext()) {
                val id = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name = cur.getString(
                    cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )

                if (cur.getInt(
                        cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER
                        )
                    ) > 0
                ) {
                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id), null
                    )
                    while (pCur!!.moveToNext()) {
                        val phoneNo = pCur.getString(
                            pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )

                        var contactData = ContactData(name, phoneNo, "")
                        contactList.add(contactData);

                        Log.i("test", "Name: $name")
                        Log.i("test", "Phone Number: $phoneNo")
                    }
                    pCur.close()
                }
            }
        }
        cur?.close()


    }

    private fun setrecycleView() {

        rv_Contactlist = findViewById(R.id.rv_Contactlist) as RecyclerView

        rv_Contactlist!!.layoutManager = layoutManagerObj

        rv_Contactlist!!.adapter = ContactAdapter(contactList, this)

        rv_Contactlist!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {

                    visibleItemCount = layoutManagerObj!!.getChildCount()
                    totalItemCount = layoutManagerObj!!.getItemCount()
                    pastVisiblesItems = layoutManagerObj!!.findFirstVisibleItemPosition()

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)
                    ) {
                        loading = true;
                    }

                }
            }
        })

    }

}
