package com.example.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_question.*

class MainActivity : AppCompatActivity() {
    private val questions = arrayListOf<Question>()
    private val questionAdapter = com.example.madlevel2task2.QuestionAdapter(questions)
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews(){
        binding.rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvQuestions.adapter = questionAdapter
        binding.rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(binding.rvQuestions)

        for (i in Question.QUESTIONS.indices){
            questions.add(Question(Question.QUESTIONS[i], Question.ANSWERS[i]))
        }
        questionAdapter.notifyDataSetChanged()
    }

    private fun createItemTouchHelper(): ItemTouchHelper{
        val callback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                //If the user swipes to the LEFT and the answer is CORRECT
                if (direction == ItemTouchHelper.LEFT && Question.ANSWERS[position] == "false"){
                    questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()
                }
                //If the user swipes to the LEFT and the answer is INCORRECT
                if (direction == ItemTouchHelper.LEFT && Question.ANSWERS[position] == "true"){
                    Snackbar.make(tvQuestion, "The given answer is incorrect, the question shown will not be deleted", Snackbar.LENGTH_SHORT).show()
                }
                //If the user swipes to the RIGHT and the answer is CORRECT
                if (direction == ItemTouchHelper.RIGHT && Question.ANSWERS[position] == "true"){
                    questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()
                }
                //If the user swipes to the RIGHT and the answer is INCORRECT
                if (direction == ItemTouchHelper.RIGHT && Question.ANSWERS[position] == "false"){
                    Snackbar.make(tvQuestion, "The given answer is incorrect, the question shown will not be deleted", Snackbar.LENGTH_SHORT).show()
                }

            }
        }
        return ItemTouchHelper(callback)
    }
}