package augusto108.ces.tqidesafiopoodio;

import augusto108.ces.tqidesafiopoodio.config.CarregarDados;

public class Main {
    public static void main(String[] args) {
        CarregarDados.abrirSessao();
        CarregarDados.persistirDados();
        CarregarDados.encerrarSessao();
    }
}
