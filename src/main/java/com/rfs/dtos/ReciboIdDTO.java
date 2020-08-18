package com.rfs.dtos;

public class ReciboIdDTO {

    private Integer id;

    private String recibo;


    public ReciboIdDTO() {}


    public ReciboIdDTO(Integer id,
                       String recibo
                       ) {
        this.id = id;
        this.recibo = recibo;

    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }


}
