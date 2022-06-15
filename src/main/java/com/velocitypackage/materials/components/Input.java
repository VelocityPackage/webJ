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
        super();
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
                text.addStyle(HyperTextElement.STYLE.MARGIN_TOP, "10px");
                text.addStyle(HyperTextElement.STYLE.MARGIN_BOTTOM, "10px");
                text.addStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "10px");
                text.addClass("form-control");
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
                password.addStyle(HyperTextElement.STYLE.MARGIN_TOP, "10px");
                password.addStyle(HyperTextElement.STYLE.MARGIN_BOTTOM, "10px");
                password.addStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "10px");
                password.addClass("form-control");
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
                submit.addStyle(HyperTextElement.STYLE.MARGIN_TOP, "10px");
                submit.addStyle(HyperTextElement.STYLE.MARGIN_BOTTOM, "10px");
                submit.addStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "10px");
                submit.addClass("btn", "btn-primary");
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
                reset.addStyle(HyperTextElement.STYLE.MARGIN_TOP, "10px");
                reset.addStyle(HyperTextElement.STYLE.MARGIN_BOTTOM, "10px");
                reset.addStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "10px");
                reset.addClass("btn", "btn-primary");
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
                email.addStyle(HyperTextElement.STYLE.MARGIN_TOP, "10px");
                email.addStyle(HyperTextElement.STYLE.MARGIN_BOTTOM, "10px");
                email.addStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "10px");
                email.addClass("form-control");
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
                date.addStyle(HyperTextElement.STYLE.MARGIN_TOP, "10px");
                date.addStyle(HyperTextElement.STYLE.MARGIN_BOTTOM, "10px");
                date.addStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "10px");
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
                number.addStyle(HyperTextElement.STYLE.MARGIN_TOP, "10px");
                number.addStyle(HyperTextElement.STYLE.MARGIN_BOTTOM, "10px");
                number.addStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "10px");
                number.addClass("form-control");
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
