package algonquin.cst2335.sing1993;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import algonquin.cst2335.sing1993.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.sing1993.databinding.ReceiveMessageBinding;
import algonquin.cst2335.sing1993.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatMessage chat = new ChatMessage("", "", false);
    private RecyclerView.Adapter myAdapter;
    ;
    private ChatMessage MessageNew;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
    String currentDateandTime = sdf.format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if (messages == null)
        {
            chatModel.messages.postValue(messages = new ArrayList<>());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
        builder.setMessage( "Do you want to delete the message :" + messages);
        builder.setTitle("Question:");
        builder.setPositiveButton("Yes",(dialog,cl) ->{});
        builder.setNegativeButton("No",(dialog,cl) ->{});
        builder.create().show();

        binding= ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sendButton.setOnClickListener(click -> {
            String message = binding.textInput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            String time = sdf.format(new Date());
            boolean isSent = true;

            ChatMessage chatMessage = new ChatMessage(message, time, isSent);
            messages.add(chatMessage);

            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");

        });
        binding.recieve.setOnClickListener(click -> {
            chat = new ChatMessage(binding.textInput.getText().toString(), currentDateandTime, false);
            messages.add(chat);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");
        });

        binding.recyclerView.setAdapter(myAdapter=  new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding sendBinding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(sendBinding.getRoot());
                }
                else {
                    ReceiveMessageBinding receiveBinding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(receiveBinding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText("");
                holder.timeText.setText("");
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(currentDateandTime);
            }

            @Override
            public int getItemCount() {

                return messages.size();
            }

            @Override public int getItemViewType(int position) {
                ChatMessage obj = messages.get(position);
                if (obj.isSentButton)
                {
                    return 0;
                } else
                {
                    return 1;
                }
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    class MyRowHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}