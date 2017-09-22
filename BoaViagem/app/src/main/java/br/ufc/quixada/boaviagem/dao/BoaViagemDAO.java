package br.ufc.quixada.boaviagem.dao;

import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.boaviagem.dominio.Gasto;
import br.ufc.quixada.boaviagem.dominio.Viagem;

/**
 * Created by Anibal on 21/09/2017.
 */
public class BoaViagemDAO {

    private static ArrayList<Viagem> listaViagens = new ArrayList<Viagem>();
    private static ArrayList<Gasto> listaGastos = new ArrayList<Gasto>();


    public BoaViagemDAO() {

    }


    public List<Viagem> listarViagens() {
        return listaViagens;
    }

    public Viagem buscarViagemPorId(Long id) {
        for (Viagem viagem : listaViagens) {
            if (viagem.getId().equals(id))
                return viagem;
        }
        return null;
    }

    public long inserirViagem(Viagem viagem) {
        long count = listaViagens.size() + 1;
        viagem.setId(count);
        listaViagens.add(viagem);
        return viagem.getId();
    }

    public long atualizar(Viagem viagem) {
        Viagem trip = this.buscarViagemPorId(viagem.getId());
        trip.setDataChegada(viagem.getDataChegada());
        trip.setDataSaida(viagem.getDataSaida());
        trip.setDestino(viagem.getDestino());
        trip.setOrcamento(viagem.getOrcamento());
        trip.setQuantidadePessoas(viagem.getQuantidadePessoas());
        trip.setTipoViagem(viagem.getTipoViagem());
        return trip.getId();
    }

    public boolean removerViagem(Long id) {
        this.listaViagens.remove(buscarViagemPorId(id));
        return true;
    }

    public List<Gasto> listarGastos(Viagem viagem) {
        return this.listaGastos;
    }

    public Gasto buscarGastoPorId(Long id) {
        for (Gasto gasto : listaGastos) {
            if (gasto.getId().equals(id))
                return gasto;
        }
        return null;
    }

    public long inserirGasto(Gasto gasto) {
        long count = listaGastos.size() + 1;
        gasto.setId(count);
        this.listaGastos.add(gasto);
        return gasto.getId();
    }

    public long atualizarGasto(Gasto gasto) {
        Gasto spent = this.buscarGastoPorId(gasto.getId());
        spent.setData(gasto.getData());
        spent.setCategoria(gasto.getCategoria());
        spent.setDescricao(gasto.getDescricao());
        spent.setValor(gasto.getValor());
        spent.setViagemId(gasto.getId());
        return spent.getId();
    }

    public boolean removerGasto(Long id) {
        Gasto gasto = buscarGastoPorId(id);
        listaGastos.remove(gasto);
        return true;
    }

    public boolean removerGastosViagem(Long idViagem) {
        for (Gasto gasto : listaGastos) {
            if (gasto.getViagemId().equals(idViagem)) {
                listaGastos.remove(gasto);
            }
        }
        return true;
    }

    public double calcularTotalGasto(Viagem viagem) {
        double total = 0;
        for (Gasto gasto : listaGastos) {
            if (gasto.getViagemId().equals(viagem.getId())) {
                total += gasto.getValor();
            }
        }
        return total;
    }

}
