package com.example.edistynytmobiiliohjelmointi2022

import Comment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentAPIBinding
import com.example.edistynytmobiiliohjelmointi2022.databinding.RecyclerviewItemRowBinding


class RecyclerAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<RecyclerAdapter.CommentHolder>() {

    private var _binding: RecyclerviewItemRowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.CommentHolder {
        _binding = RecyclerviewItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CommentHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.CommentHolder, position: Int) {

        val itemComment = comments[position]
        holder.bindComment(itemComment)

    }

        override fun getItemCount() = comments.size

        //1
        class CommentHolder(v: RecyclerviewItemRowBinding) : RecyclerView.ViewHolder(v.root), View.OnClickListener {
            //2
            private var view: RecyclerviewItemRowBinding = v
            private var comment: Comment? = null

            fun bindComment(comment : Comment)
            {
                //comment talteen
                this.comment = comment

                view.textViewCommentEmail.text = comment.email
                view.textViewCommentName.text = comment.name
                view.textViewCommentContent.text = comment.body
            }

            //3
            init {
                v.root.setOnClickListener(this)
            }

            //4
            override fun onClick(v: View) {
                val id : Int = this.comment?.id as Int

                val action = APIFragmentDirections.actionAPIFragmentToApiDetailFragment(id)

                v.findNavController().navigate(action)

                Log.d("ADVTECH", id.toString())
            }

        }

    }
