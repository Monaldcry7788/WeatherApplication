package christian.scuola.weatherapplication.parser;

import java.util.List;

public class Item
{
    private MainData main;
    private List<Weather> weather;
    private Wind wind;
    private String dt_txt;

    public MainData getMain()
    {
        return main;
    }
    public void setMain(MainData main)
    {
        this.main = main;
    }
    public List<Weather> getWeather()
    {
        return weather;
    }
    public void setWeather(List<Weather> weather)
    {
        this.weather = weather;
    }
    public Wind getWind()
    {
        return wind;
    }
    public void setWind(Wind wind)
    {
        this.wind = wind;
    }
    public String getDt_txt()
    {
        return dt_txt;
    }
    public void setDt_txt(String dt_txt)
    {
        this.dt_txt = dt_txt;
    }
}