package com.applligent.namaztime.nearByMasjid;

public class MasjidListModel {

    private String masjidID;
    private String name;
    private boolean isFavourite;
    private String fajar;
    private String zuhar;
    private String asar;
    private String magrib;
    private String isha;
    private String jumaTime;
    private double lat;
    private double lng;

    public String getMasjidID() { return masjidID; }
    public void setMasjidID(String value) { this.masjidID = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public boolean getIsFavourite() { return isFavourite; }
    public void setIsFavourite(boolean value) { this.isFavourite = value; }

    public String getFajar() { return fajar; }
    public void setFajar(String value) { this.fajar = value; }

    public String getZuhar() { return zuhar; }
    public void setZuhar(String value) { this.zuhar = value; }

    public String getAsar() { return asar; }
    public void setAsar(String value) { this.asar = value; }

    public String getMagrib() { return magrib; }
    public void setMagrib(String value) { this.magrib = value; }

    public String getIsha() { return isha; }
    public void setIsha(String value) { this.isha = value; }

    public String getJumaTime() { return jumaTime; }
    public void setJumaTime(String value) { this.jumaTime = value; }

    public double getLat() { return lat; }
    public void setLat(double value) { this.lat = value; }

    public double getLng() { return lng; }
    public void setLng(double value) { this.lng = value; }


}
