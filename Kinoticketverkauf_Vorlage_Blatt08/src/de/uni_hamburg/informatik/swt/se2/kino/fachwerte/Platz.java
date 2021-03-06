package de.uni_hamburg.informatik.swt.se2.kino.fachwerte;

/**
 * Eine Sitzplatzangabe in einem Kinosaal. Der Platz setzt sich zusammen aus der
 * Reihe und dem Sitz in dieser Reihe.
 * 
 * @author SE2-Team
 * @version SoSe 2015
 */
public final class Platz
{
    private final int _reihe;
    private final int _sitz;

    /**
     * Wählt einen Platz aus.
     * 
     * @param reihe
     *            die Sitzreihe.
     * @param sitz
     *            die Nummer des Sitzes in seiner Sitzreihe.
     * 
     * @require reihe >= 0
     * @require sitz >= 0
     */
    public static Platz get(int reihe, int sitz)
    {
        assert reihe >= 0 : "Vorbedingung verletzt: reihe >= 0";
        assert sitz >= 0 : "Vorbedingung verletzt: sitz >= 0";

        return new Platz(reihe, sitz);
    }

    private Platz(int reihe, int sitz)
    {
        _reihe = reihe;
        _sitz = sitz;
    }

    /**
     * Gibt die Sitzreihe zurueck, in der sich dieser Platz befindet.
     */
    public int getReihe()
    {
        return _reihe;
    }

    /**
     * Gibt die Nummer dieses Sitzes in seiner Sitzreihe zurueck.
     */
    public int getSitz()
    {
        return _sitz;
    }

    @Override
    public boolean equals(Object o)
    {
        return (o instanceof Platz) && equals((Platz) o);
    }

    private boolean equals(Platz andererPlatz)
    {
        return (_reihe == andererPlatz._reihe) && (_sitz == andererPlatz._sitz);
    }

    @Override
    public int hashCode()
    {
        return 10007 * _reihe + _sitz;
    }

    @Override
    public String toString()
    {
        String reihe = "" + (_reihe + 1);
        String sitz = "" + _sitz;
        if((_reihe + 1) < 10)
        {
            reihe = "0" + (_reihe + 1);
        }
        else if(_sitz < 10)
        {
            sitz = "0" + _sitz;
        }
        return "Reihe " + reihe + "," + " Sitz " + sitz;
    }
}
