package edu.neu.madcourse.stick_it_to_em;
public class ChatActivity
{
    private String sender;
    private String receiver;
    private String timeStamp;
    private String sticker;


    public ChatActivity(){}

    public ChatActivity(String sender, String receiver, String timeStamp, String sticker)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.timeStamp = timeStamp;
        this.sticker = sticker;
    }


    public String getSender()
    {
        return this.sender;
    }

    public String getReceiver()
    {
        return this.receiver;
    }

    public String getTimeStamp()
    {
        return this.timeStamp;
    }

    public String getSticker()
    {
        return this.sticker;
    }



}

