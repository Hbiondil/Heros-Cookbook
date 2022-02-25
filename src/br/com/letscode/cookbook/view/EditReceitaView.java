package br.com.letscode.cookbook.view;

import java.util.Locale;

import br.com.letscode.cookbook.domain.Ingrediente;
import br.com.letscode.cookbook.domain.Receita;
import br.com.letscode.cookbook.domain.Rendimento;
import br.com.letscode.cookbook.enums.TipoMedida;
import br.com.letscode.cookbook.enums.TipoRendimento;

public class EditReceitaView {
    private Receita receita;

    public EditReceitaView(Receita receita, int origem) {
        if(origem == 1) {
            this.receita = new Receita(receita);
        } else {
            this.receita = new Receita(receita.getNome(), receita.getCategoria());
        }
    }

    public Receita edit() {
        boolean confirm = false;
        boolean fim = true;
        String gravar = "";

        do {
            gravar = editMenu();
            new ReceitaView(receita).fullView(System.out);
            if (gravar == "S" || gravar == "X") {
                fim = false;
                if (gravar == "S") confirm = true;
            }
        } while(fim);

        if (confirm) {
            return receita;
        } else {
            return null;
        }
    }

    private String editMenu() {
        String[] options = new String[8];
        StringBuilder sb = new StringBuilder("#".repeat(100));

        sb.append("%n  T : Tempo de Preparo %n");
        options[2] = "T";
        sb.append("  R : Rendimento %n");
        options[3] = "R";
        sb.append("  I : Ingredientes %n");
        options[4] = "I";
        sb.append("  P : Preparos %n");
        options[5] = "P";

        sb.append("%n  G : Gravar a receita %n");
        options[6] = "G";

        sb.append("  X : Sair sem gravar%n");
        options[7] = "X";

        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "T":
                tempoPreparo();
                break;
            case "R":
                rendimento();
                break;
            case "I":
                ingrediente();
                break;
            case "P":
                preparo();
                break;
            case "X":
                System.out.println("Saindo sem gravar");
                return "X";
            case "S":
                System.out.println("Gravando e saindo");
                return "S";
            default:
                System.out.println("Opção Inválida!");
        }
        return "";
    }

    private boolean menuIngredientes() {
        String[] options = new String[3];
        StringBuilder sb = new StringBuilder("#".repeat(100));

        sb.append("%n").append("  + : Adicionar Ingrediente %n");
        options[0] = "+";

        if (receita.getIngredientes().size() != 0) {
            sb.append("  - : Remover  Ingrediente %n");
            options[1] = "-";
        }

        sb.append("  # ").append("# ".repeat(48)).append("%n");
        sb.append("  X : Sair dos Ingredientes %n");
        options[2] = "X";
        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "+":
                addIngrediente();
                break;
            case "-":
                delIngrediente();
                break;
            case "X":
                System.out.println("Saindo dos Ingredientes");
                return false;
            default:
                System.out.println("Opção Inválida!");
        }
        return true;
    }

    private boolean menuPreparos() {
        String[] options = new String[3];
        StringBuilder sb = new StringBuilder("#".repeat(100));

        sb.append("%n").append("  + : Adicionar Preparo %n");
        options[0] = "+";

        if (receita.getPreparo().size() != 0) {
            sb.append("  - : Remover Preparo %n");
            options[1] = "-";
        }

        sb.append("  # ").append("# ".repeat(48)).append("%n");
        sb.append("  X : Sair do Preparo %n");
        options[2] = "X";
        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "+":
                addPreparo();
                break;
            case "-":
                delPreparo();
                break;
            case "X":
                System.out.println("Saindo do Preparo");
                return false;
            default:
                System.out.println("Opção Inválida!");
        }
        return true;
    }

    private void preparo() {
        do {
            new ReceitaView(receita).fullView(System.out);
        } while (menuPreparos());
    }

    private void ingrediente() {
        do {
            new ReceitaView(receita).fullView(System.out);
        } while (menuIngredientes());
    }

    private void addIngrediente() {

        String nome = ConsoleUtils.getUserInput("Informe o nome do Ingrediente: ");
        Double quantidade = ConsoleUtils.getUserDouble("Informe a quantidade: ");

        StringBuilder sb = new StringBuilder("Informe o tipo de medida: \n");
        String[] options = new String[TipoMedida.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, TipoMedida.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        TipoMedida tipoMedida = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                tipoMedida = TipoMedida.values()[i];
                break;
            }
        }

        Ingrediente ingrediente = new Ingrediente(nome, quantidade, tipoMedida);
        receita.setIngrediente(ingrediente);

    }

    private void delIngrediente() {
        StringBuilder sb = new StringBuilder("Informe o ingrediente para excluir: \n");
        String[] options = new String[receita.getIngredientes().size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, receita.getIngredientes().get(i).getNome()));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                receita.delIngrediente(i);
                break;
            }
        }
    }

    private void addPreparo() {

        String preparo = ConsoleUtils.getUserInput("Informe o preparo: ");

        receita.setPreparo(preparo);
    }

    private void delPreparo() {
        StringBuilder sb = new StringBuilder("Informe o preparo para excluir: \n");
        String[] options = new String[receita.getPreparo().size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, receita.getPreparo().get(i)));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                receita.delPreparo(i);
                break;
            }
        }
    }

    private void rendimento() {
        StringBuilder sb = new StringBuilder("Informe o Rendimento: \n");
        String[] options = new String[TipoRendimento.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, TipoRendimento.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        TipoRendimento tipoRendimento = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                tipoRendimento = TipoRendimento.values()[i];
                break;
            }
        }
        int rendimentoMin = ConsoleUtils.getUserInteger("Informe o rendimento mínimo: ");
        int rendimentoMax = ConsoleUtils.getUserInteger("Informe o rendimento máximo: ");
        Rendimento rendimento = new Rendimento(rendimentoMin, rendimentoMax, tipoRendimento);
        receita.setRendimento(rendimento);
    }

    private void tempoPreparo() {
        double tempo = ConsoleUtils.getUserDouble("Informe o tempo de preparo: ");
        receita.setTempoPreparo(tempo);
    }

}
