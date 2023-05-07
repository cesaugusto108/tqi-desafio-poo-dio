package augusto108.ces.tqidesafiopoodio.config;

import augusto108.ces.tqidesafiopoodio.dao.Dao;
import augusto108.ces.tqidesafiopoodio.dominio.entidades.Bootcamp;
import augusto108.ces.tqidesafiopoodio.dominio.entidades.Dev;
import augusto108.ces.tqidesafiopoodio.dominio.entidades.Nome;
import augusto108.ces.tqidesafiopoodio.dominio.entidades.Pessoa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarregarDev {
    public static void cadastrarDevs(Dao dao) {
        final List<Pessoa> devs = new ArrayList<>();
        final List<Bootcamp> bootcamps = dao.listarBootcamps();

        boolean loop = false;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("\nNome: ");
            final String nome = scanner.next();
            System.out.print("Sobrenome: ");
            final String sobrenome = scanner.next();
            System.out.print("Idade: ");
            final int idade = scanner.nextInt();
            System.out.print("Email: ");
            final String email = scanner.next();
            System.out.print("Nível: ");
            final int nivel = scanner.nextInt();

            final Dev dev = new Dev(new Nome(nome, sobrenome), idade, email, nivel);

            devs.add(dev);

            System.out.println("\nBootcamps disponíveis:");
            for (Bootcamp bootcamp : bootcamps) {
                System.out.println(bootcamp.getId() + " - " + bootcamp.getDescricao());
            }

            System.out.print("\nEscolha um dos bootcamps (inserir número): ");
            final long escolha = scanner.nextLong();

            dev.getBootcamps().add(dao.buscarBootcamp(escolha));

            System.out.print("\nDeseja cadastrar outro dev? (sim/nao) ");
            final String resposta = scanner.next();

            loop = resposta.equalsIgnoreCase("sim");
        } while (loop);

        scanner.close();

        dao.persistirPessoas(devs);
    }
}
