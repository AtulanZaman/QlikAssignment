package com.example.messages;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Message {
    private @Id @GeneratedValue Long id;
    private String text;
    private @Transient boolean isPalindrome;

    Message(){}

    Message(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public Long getId(){
        return this.id;
    }

    public boolean getIsPalindrome(){
        String text = this.getText();
        if (text.length()==1) return true;
        Integer endIndex = Math.floorDiv(text.length(), 2);
        for(int i=0; i<=endIndex; i++){
            if(text.charAt(i) != text.charAt(text.length()-1-i)){
                return false;
            }
        }
        return true;
    };

    public void setText(String text){
        this.text = text;
    }

    public void setId(Long id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message m = (Message) o;
        return this.id == m.id;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.text);
    }
    
    @Override
    public String toString() {
      return "Message{" + "id=" + this.id + ", text='" + this.text + '}';
    }
}



