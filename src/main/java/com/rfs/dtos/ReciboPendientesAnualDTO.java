package com.rfs.dtos;

/**
 * Created by alvaro on 11/2/17.
 */
public class ReciboPendientesAnualDTO {

    private Integer ano;
    private Long dic;
    private Long nov;
    private Long oct;
    private Long sept;
    private Long ago;
    private Long jul;
    private Long jun;
    private Long may;
    private Long abr;
    private Long mar;
    private Long feb;
    private Long ene;

    public ReciboPendientesAnualDTO(Integer ano,
                                    Long dic,
                                    Long nov,
                                    Long oct,
                                    Long sept,
                                    Long ago,
                                    Long jul,
                                    Long jun,
                                    Long may,
                                    Long abr,
                                    Long mar,
                                    Long feb,
                                    Long ene) {
        this.ano = ano;
        this.dic =dic;
        this.nov = nov;
        this.oct = oct;
        this.sept = sept;
        this.ago = ago;
        this.jul = jul;
        this.jun = jun;
        this.may = may;
        this.abr = abr;
        this.mar = mar;
        this.feb = feb;
        this.ene = ene;

    }

//    public ReciboPendientesAnualDTO(
//            Integer ano,
//                                    Long dic
//                                    ) {
//        this.ano = ano;
//        this.dic =dic;
//        this.nov = nov;
//        this.oct = oct;
//        this.set = set;
//        this.ago = ago;
//        this.jul = jul;
//        this.jun = jun;
//        this.may = may;
//        this.abr = abr;
//        this.mar = mar;
//        this.feb = feb;
//        this.ene = ene;
//
//    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Long getDic() {
        return dic;
    }

    public void setDic(Long dic) {
        this.dic = dic;
    }

    public Long getNov() {
        return nov;
    }

    public void setNov(Long nov) {
        this.nov = nov;
    }

    public Long getOct() {
        return oct;
    }

    public void setOct(Long oct) {
        this.oct = oct;
    }

    public Long getSept() {
        return sept;
    }

    public void setSet(Long sept) {
        this.sept = sept;
    }

    public Long getAgo() {
        return ago;
    }

    public void setAgo(Long ago) {
        this.ago = ago;
    }

    public Long getJul() {
        return jul;
    }

    public void setJul(Long jul) {
        this.jul = jul;
    }

    public Long getJun() {
        return jun;
    }

    public void setJun(Long jun) {
        this.jun = jun;
    }

    public Long getMay() {
        return may;
    }

    public void setMay(Long may) {
        this.may = may;
    }

    public Long getAbr() {
        return abr;
    }

    public void setAbr(Long abr) {
        this.abr = abr;
    }

    public Long getMar() {
        return mar;
    }

    public void setMar(Long mar) {
        this.mar = mar;
    }

    public Long getFeb() {
        return feb;
    }

    public void setFeb(Long feb) {
        this.feb = feb;
    }

    public Long getEne() {
        return ene;
    }

    public void setEne(Long ene) {
        this.ene = ene;
    }
}
