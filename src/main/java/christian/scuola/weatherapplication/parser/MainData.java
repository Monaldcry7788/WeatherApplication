package christian.scuola.weatherapplication.parser;

public class MainData {
    private double temp;
    private double feels_like;
    private int pressure;
    private int humidity;
    private int grnd_level;

    public double getTemp()
    {
        return temp;
    }
    public void setTemp(double temp)
    {
        this.temp = temp;
    }
    public double getFeelsLike()
    {
        return feels_like;
    }
    public void setFeelsLike(double feelsLike)
    {
        this.feels_like = feelsLike;
    }
    public int getPressure()
    {
        return pressure;
    }
    public void setPressure(int pressure)
    {
        this.pressure = pressure;
    }
    public int getHumidity()
    {
        return humidity;
    }
    public void setHumidity(int humidity)
    {
        this.humidity = humidity;
    }

    public int getGrndLevel()
    {
        return grnd_level;
    }

    public void getGrndLevel(int grnd_level)
    {
        this.grnd_level = grnd_level;
    }
}