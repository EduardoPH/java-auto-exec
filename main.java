import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    File currentDir = new File(".");
    File[] packages = currentDir.listFiles(File::isDirectory);
    while(true) {
      if(packages != null) {
        limparTela();
        System.out.println("Listas de Exercícios:");
        for(int i = 0; i < packages.length; i++) {
          System.out.println((i + 1) + ". " + packages[i].getName());
        }
        System.out.println((packages.length + 1) + ". Sair \n");

        System.out.print("Selecione uma opção: ");

        int listaIndex = sc.nextInt() - 1;
        sc.nextLine();

        if(listaIndex == packages.length) {
          break; // Fechar programa, caso seja selecionado a ultima opção, que no caso é a 2
        }

        if(listaIndex >= 0 && listaIndex < packages.length) {
          File packageSelecionado = packages[listaIndex];
          File[] exercicios = packageSelecionado.listFiles((dir, name) -> name.endsWith(".java"));

          if(exercicios != null) {
            limparTela();
            System.out.println("\nExercícios na lista " +  packageSelecionado.getName() + ":");
          
            for(int i = 0; i < exercicios.length; i++) {
              String nomeExercicio = exercicios[i].getName();
              if (nomeExercicio.endsWith(".java")) {
                nomeExercicio = nomeExercicio.substring(0, nomeExercicio.length() - 5);
              }
              System.out.println((i + 1) + ". " + nomeExercicio);
            }

            System.out.println((exercicios.length + 1) + ". Execução automática da lista: " + packageSelecionado.getName());
          
            System.out.print("\nSelecione um exercício para executar: ");
            int exercicioIndex = sc.nextInt() - 1;
            sc.nextLine(); //Para evitar o erro de buffer de teclado

            if(exercicioIndex == exercicios.length) {
              for(File exercicio : exercicios) {
                executarExercicio(exercicio, sc);
              }
            } else if(exercicioIndex >= 0 && exercicioIndex < exercicios.length) {
              File exercicioSelecionado = exercicios[exercicioIndex];
              executarExercicio(exercicioSelecionado, sc);
            } else {
              System.out.println("Exercício inválido.");
            }
          } else {
            System.out.println("Nenhum exercício encontrado na lista selecionada.");
          } 
        } else {
          System.out.println("Lista inválida.");
        }
      } else {
        System.out.println("Nenhuma lista de exercícios encontrada.");
      }
    }
  }

  private static void executarExercicio(File exercicio, Scanner sc) {
    try {
      limparTela();

      System.out.println("Executando exercício: " + exercicio.getName() + '\n');
      ProcessBuilder pb = new ProcessBuilder("java", exercicio.getPath());
      pb.inheritIO();
      Process p = pb.start();
      p.waitFor();
      System.out.println("\nExecução finalizada.");
      System.out.println("\nPressione Enter para continuar...");
      sc.nextLine(); // Aguardar o enter do usuário
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void limparTela() {
    // Nao sabia que isso existia mas é uma mao na roda. 
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
