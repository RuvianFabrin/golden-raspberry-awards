package com.outsera.awards.golden_raspberry_awards.model;

import java.util.HashSet;
import java.util.Set;

public class Filme {
    private Long id;

    private Integer ano;
    private String titulo;
    private String estudios;
    private boolean vencedor;

    private Set<Produtor> produtores = new HashSet<>();

    public Filme() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public boolean isVencedor() {
        return vencedor;
    }

    public void setVencedor(boolean vencedor) {
        this.vencedor = vencedor;
    }

    public Set<Produtor> getProdutores() {
        return produtores;
    }

    public void setProdutores(Set<Produtor> produtores) {
        this.produtores = produtores;
    }

    public void addProdutor(Produtor produtor) {
        if (!this.produtores.contains(produtor)) {
            this.produtores.add(produtor);
            produtor.getFilmes().add(this);
        }

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        Filme other = (Filme) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Filme [id=" + id + ", ano=" + ano + ", titulo=" + titulo + ", estudios=" + estudios + ", vencedor="
                + vencedor + "]";
    }

}
