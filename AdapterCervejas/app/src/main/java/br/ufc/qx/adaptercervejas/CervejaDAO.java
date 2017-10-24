package br.ufc.qx.adaptercervejas;

import java.util.*;

/**
 * Created by Anibal on 24/10/2017.
 */

class CervejaDAO {

    private List<Cerveja> cervejaList;

    public static CervejaDAO instance;

    public static CervejaDAO getInstance() {
        if (instance == null)
            instance = new CervejaDAO();
        return instance;
    }

    private CervejaDAO(){
        this.cervejaList  = new ArrayList<Cerveja>();
        Cerveja skol = new Cerveja(1L, "Skol", "Ambev", R.drawable.skol);
        Cerveja antartica = new Cerveja(2L,"Antartica", "Ambev", R.drawable.antartica);
        Cerveja amstel = new Cerveja(3L, "Amstel", "Heineken", R.drawable.amstel);
        cervejaList.add(skol);
        cervejaList.add(antartica);
        cervejaList.add(amstel);

    }

    public List<Cerveja> todasAsCervejas() {
        return this.cervejaList;
    }
}
