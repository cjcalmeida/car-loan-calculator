package com.cjcalmeida.carloan.core.events;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public interface IEvent<T> extends Serializable {

    String getSpecversion();
    String getType();
    void setType(String type);
    UUID getId();
    void setId(UUID id);
    Date getTime();
    void setTime(Date time);
    String getDatacontenttype();
    void setDatacontenttype(String datacontenttype);
    T getData();
    void setData(T data);
}
