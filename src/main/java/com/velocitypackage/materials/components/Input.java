package com.velocitypackage.materials.components;

import java.util.Map;
import java.util.Objects;

public class Input extends Component
{
    private final String id;
    private final String text;
    private final TYPE type;
    
    public Input(String text, TYPE type)
    {
        this.id = String.valueOf(this.hashCode());
        this.text = Objects.requireNonNullElse(text, "");
        this.type = type;
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    public final String getFormId()
    {
        return this.id;
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
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    text.addStyle(style.getKey(), style.getValue());
                }
                for (Bootstrap bootstrapClass : classes)
                {
                    text.addClass(bootstrap(bootstrapClass));
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
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    password.addStyle(style.getKey(), style.getValue());
                }
                for (Bootstrap bootstrapClass : classes)
                {
                    password.addClass(bootstrap(bootstrapClass));
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
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    submit.addStyle(style.getKey(), style.getValue());
                }
                for (Bootstrap bootstrapClass : classes)
                {
                    submit.addClass(bootstrap(bootstrapClass));
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
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    reset.addStyle(style.getKey(), style.getValue());
                }
                for (Bootstrap bootstrapClass : classes)
                {
                    reset.addClass(bootstrap(bootstrapClass));
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
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    email.addStyle(style.getKey(), style.getValue());
                }
                for (Bootstrap bootstrapClass : classes)
                {
                    email.addClass(bootstrap(bootstrapClass));
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
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    date.addStyle(style.getKey(), style.getValue());
                }
                for (Bootstrap bootstrapClass : classes)
                {
                    date.addClass(bootstrap(bootstrapClass));
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
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    number.addStyle(style.getKey(), style.getValue());
                }
                for (Bootstrap bootstrapClass : classes)
                {
                    number.addClass(bootstrap(bootstrapClass));
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
