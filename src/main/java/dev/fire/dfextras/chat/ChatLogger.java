package dev.fire.dfextras.chat;

import net.minecraft.text.Text;

import java.util.ArrayList;

public class ChatLogger {
    public ArrayList<Text> chatLog;
    public ChatLogger() {
        chatLog = new ArrayList<>();
    }
    public void appendChat(Text text) {
        chatLog.add(text);
    }
}
