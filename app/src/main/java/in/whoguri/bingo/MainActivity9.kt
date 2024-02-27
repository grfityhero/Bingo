package `in`.whoguri.bingo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class MainActivity9 : AppCompatActivity() {
    val adapter by lazy {
        GridAdapter(this, 5, AppData.dataList) { it, b ->
            val data = AppData.dataList[it]
            data.isClicked = !b
            AppData.dataList[it] = data
            if (AppData.dataList.filter { it.isClicked }.isEmpty()) {
                restart()
                return@GridAdapter
            } else {
                recal()
            }
        }
    }
    val resultAdapter by lazy {
        ResultAdapter(this, AppData.resultList)
    }
    val averageAdapter by lazy {
        ResultAdapter(this, AppData.averageList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.title_).text = "Bingo! 9"

        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en-us")
        AppCompatDelegate.setApplicationLocales(appLocale)
//actionBar.setDisplayHomeAsUpEnabled()
        averageAdapter.setNotifyOnChange(true)
        findViewById<GridView>(R.id.grid).adapter = adapter
        findViewById<GridView>(R.id.sortGrid).adapter = resultAdapter
        findViewById<GridView>(R.id.avrageGrid).adapter = averageAdapter
        planA()
        findViewById<LinearLayout>(R.id.resetD).setOnClickListener {
            Logic.clickDs()
            recal()
            adapter.notify_()
        }
        findViewById<LinearLayout>(R.id.restart).setOnClickListener {
            restart()
        }
        findViewById<Button>(R.id.btn1).setOnClickListener {
            planA()
        }
        findViewById<Button>(R.id.btn2).setOnClickListener {
            planB()
        }
    }

    private fun planA() {
        findViewById<GridView>(R.id.sortGrid).visibility = View.VISIBLE
        findViewById<GridView>(R.id.avrageGrid).visibility = View.GONE
    }

    private fun planB() {
        findViewById<GridView>(R.id.sortGrid).visibility = View.GONE
        findViewById<GridView>(R.id.avrageGrid).visibility = View.VISIBLE
    }

    private fun recal() {
        AppData.dataList = Logic.calResult5(AppData.dataList, 1)
        AppData.dataList = Logic.calResult5(AppData.dataList, 2)
        AppData.dataList = Logic.calResult(AppData.dataList)
        AppData.dataList = Logic.calResult7(AppData.dataList)
        AppData.dataList = Logic.calResult8(AppData.dataList)

//        AppData.dataList = Logic.calResult3(AppData.dataList)
        val temp = ArrayList<Data>()
        AppData.dataList.forEach {
            if (it.finalValue5 > 0 && !it.isClicked) {
                temp.add(it)
            }
        }
        AppData.resultList.clear()
        AppData.averageList.clear()
        averageAdapter.clear()
        temp.sortedByDescending { it.finalValue5 }.forEach {
            if (AppData.resultList.size < 10 && it.number != 13) {
                AppData.resultList.add(it.code)
            }
        }
        AppData.averageList = Logic.calAverage5(AppData.dataList)
        resultAdapter.notifyDataSetChanged()
        averageAdapter.addAll(AppData.averageList)
//                adapter3.notifyDataSetChanged()

    }

    fun restart() {
        AppData.reset()
        startActivity(Intent(this, MainActivity9::class.java))
        overridePendingTransition(0, 0)
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()

        val temp = ArrayList<Data>()
        AppData.dataList.forEach {
            if (it.finalValue5 > 0 && !it.isClicked) {
                temp.add(it)
            }
        }
        AppData.resultList.clear()
        AppData.averageList.clear()
        averageAdapter.clear()
        temp.sortedByDescending { it.finalValue5 }.forEach {
            if (AppData.resultList.size < 10 && it.number != 13) {
                AppData.resultList.add(it.code)
            }
        }
        AppData.averageList = Logic.calAverage5(AppData.dataList)
        averageAdapter.addAll(AppData.averageList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item2: MenuItem = menu.findItem(R.id.button_item2)
        item2.setOnMenuItemClickListener { it ->
            startActivity(Intent(this, MainActivity6::class.java))
            overridePendingTransition(0, 0)
            return@setOnMenuItemClickListener true
        }
        val item3: MenuItem = menu.findItem(R.id.button_item3)
        item3.setOnMenuItemClickListener { it ->
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0, 0)
            return@setOnMenuItemClickListener true
        }

        val item7: MenuItem = menu.findItem(R.id.button_item7)
        item7.setOnMenuItemClickListener { it ->
            startActivity(Intent(this, MainActivity7::class.java))
            overridePendingTransition(0, 0)
            return@setOnMenuItemClickListener true
        }
        val item8: MenuItem = menu.findItem(R.id.button_item8)
        item8.setOnMenuItemClickListener { it ->
            startActivity(Intent(this, MainActivity8::class.java))
            overridePendingTransition(0, 0)
            return@setOnMenuItemClickListener true
        }
        val item1: MenuItem = menu.findItem(R.id.button_item1)
        item1.setOnMenuItemClickListener { it ->
            startActivity(Intent(this, MainActivity5::class.java))
            overridePendingTransition(0, 0)
            return@setOnMenuItemClickListener true
        }

        return true
    }

}