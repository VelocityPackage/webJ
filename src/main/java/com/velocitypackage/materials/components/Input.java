package com.velocitypackage.materials.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Input implements Component
{
    private final String id;
    private final String text;
    private final TYPE type;
    private final Map<HyperTextElement.STYLE, String> styles;
    
    public Input(String text, TYPE type)
    {
        this.id = String.valueOf(this.hashCode());
        this.text = Objects.requireNonNullElse(text, "");
        this.type = type;
        styles = new HashMap<>();
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    @Override
    public void putStyle(HyperTextElement.STYLE option, String value)
    {
        styles.put(option, value);
    }
    
    public final String getFormId()
    {
        return this.id;
    }
    
    @Override
    public void onInteract(String id, Map<String, String> inputs)
    {
    }
    
    @Override
    public HyperTextElement getContent()
    {
        switch (type)
        {
            case TEXT:
                HyperTextElement text = new HyperTextElement(HyperTextElement.TAG.INPUT);
                text.setPlaceholder(this.text);
                text.addId(id);
                text.setType("text");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    text.addStyle(style.getKey(), style.getValue());
                }
                return text;
            case PASSWORD:
                HyperTextElement password = new HyperTextElement(HyperTextElement.TAG.INPUT);
                password.setPlaceholder(this.text);
                password.addId(id);
                password.setType("password");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    password.addStyle(style.getKey(), style.getValue());
                }
                return password;
            case SUBMIT:
                HyperTextElement submit = new HyperTextElement(HyperTextElement.TAG.INPUT);
                submit.setValue(this.text);
                submit.addId(id);
                submit.setType("submit");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    submit.addStyle(style.getKey(), style.getValue());
                }
                return submit;
            case RESET:
                HyperTextElement reset = new HyperTextElement(HyperTextElement.TAG.INPUT, this.text);
                reset.addId(id);
                reset.setType("reset");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    reset.addStyle(style.getKey(), style.getValue());
                }
                return reset;
            case EMAIL:
                HyperTextElement email = new HyperTextElement(HyperTextElement.TAG.INPUT);
                email.setPlaceholder(this.text);
                email.addId(id);
                email.setType("email");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    email.addStyle(style.getKey(), style.getValue());
                }
                return email;
            case DATE:
                HyperTextElement date = new HyperTextElement(HyperTextElement.TAG.INPUT, this.text);
                date.addId(id);
                date.setType("date");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    date.addStyle(style.getKey(), style.getValue());
                }
                return date;
            case NUMBER:
                HyperTextElement number = new HyperTextElement(HyperTextElement.TAG.INPUT);
                number.setValue(this.text);
                number.setType("number");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    number.addStyle(style.getKey(), style.getValue());
                }
                return number;
            default:
                return new HyperTextElement(HyperTextElement.TAG.DIV);
        }
    }
    
    public enum TYPE
    {
        TEXT, PASSWORD, SUBMIT, RESET, EMAIL, DATE, NUMBER
    }
}
