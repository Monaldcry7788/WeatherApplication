package christian.scuola.weatherapplication.parser;

public class City {
    private String name;
    private String country;
    private long sunrise;
    private long sunset;
    private int population;

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public long getSunset()
    {
        return sunset;
    }

    public void setSunset(long sunset)
    {
        this.sunset = sunset;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }

    public long getSunrise()
    {
        return sunrise;
    }

    public void setSunrise(long sunrise)
    {
        this.sunrise = sunrise;
    }
}