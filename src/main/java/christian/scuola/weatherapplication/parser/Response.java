package christian.scuola.weatherapplication.parser;

import java.util.List;

public class Response
{
    private List<Item> list;
    private City city;

    public List<Item> getList()
    {
        return list;
    }
    public void setList(List<Item> list)
    {
        this.list = list;
    }
    public City getCity()
    {
        return city;
    }
    public void setCity(City city)
    {
        this.city = city;
    }
}