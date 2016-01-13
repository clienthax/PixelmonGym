package uk.co.haxyshideout.pixelgym.data;

import java.util.List;

public class GymPokemonEntry {

    String pokemonName;
    Integer level;
    List<String> moveList;

    public String getPokemonName() {
        return this.pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setPokemonLevel(Integer level) {
        this.level = level;
    }

    public List<String> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<String> moveList) {
        this.moveList = moveList;
    }

}
