package com.cjcalmeida.carloan.core.events;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class LoanEvent<T> implements IEvent<T>, Serializable {

    private static final long serialVersionUID = -3240660443505832389L;

    private String type;
    private UUID id;
    private Date time;
    private String datacontenttype;
    private T data;

    /**
     * Only for framework
     */
    @Deprecated
    public LoanEvent() {}

    public LoanEvent(String type) {
        this.type = type;
    }

    @Override
    public String getSpecversion() {
        return "1.0";
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public Date getTime() {
        return this.time;
    }

    @Override
    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String getDatacontenttype() {
        return this.datacontenttype;
    }

    @Override
    public void setDatacontenttype(String datacontenttype) {
        this.datacontenttype = datacontenttype;
    }

    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    public String printMetadata(){
           return String.format("ID: %s | Version: %s | Date: %tFT | Type: %s | Content-type: %s",
                   this.getId(),
                   this.getSpecversion(),
                   this.getTime(),
                   this.getType(),
                   this.getDatacontenttype()
           );
    }
}
