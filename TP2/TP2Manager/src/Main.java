import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Writes w = new Writes();
        Reader r = new Reader();
        Querys querys = new Querys();

        Scanner sc = new Scanner(System.in);

        //menu
        System.out.println("Bem vindo ao menu da ferramenta de monitorização");

        while(true) {
            String hostName = "127.0.0.1/161";
            System.out.println("\nSelecione uma das seguintes opções :\n" +
                    "1 -> Ativar o mecanismo de monitorização snmp ;" +
                    "\n2 -> Visualizar os 10 processos que maior percentagem de RAM utilizaram;"+
            "\n3 -> Visualizar os 10 processos que maior percentagem de RAM utilizaram num dia;"
            + "\n4 -> Visualizar os processos que não utilizam nem RAM nem CPU"
            + "\n5 -> Visuzalizar os 10 processos que mais utilizam CPU"
            );

            int opt = sc.nextInt();
            switch(opt) {
                    case 1: //fazer os pedidos à MIB e escrever no ficheiro os dados retirados
                        System.out.println("Insira o IP do host no formato tcp/udp , " +
                                "para o localhost/161 digite default");
                        String host = sc.next();
                        if(host.equals("default")) {
                            w.execWrites(hostName);
                        }
                        else w.execWrites(host);
                        break;

                    case 2:
                        System.out.println("Insira o IP do host no formato tcp/udp ," +
                                "para o localhost/161 digite default");

                        String host2 = sc.next();
                        if(!host2.equals("default")) {
                            hostName = host2;
                        }
                        Charts chart1 = new Charts("10 processos que maior percentagem de RAM utilizaram",
                         querys.getPRamAlltime(r.execReader(hostName)));
                        chart1.runChart("10 processos que maior percentagem de RAM utilizaram",
                                querys.getPRamAlltime(r.execReader(hostName)));

                        break;

                    case 3:
                        System.out.println("Insira o IP do host no formato tcp/udp ," +
                                "para o localhost/161 digite default");

                        String host3 = sc.next();
                        if(!host3.equals("default")) {
                            hostName = host3;
                        }
                        System.out.println("Introduza o dia no formato dia-mes-ano");
                        String dia = sc.next();
                        Charts chart2 = new Charts("10 processos que maior percentagem de RAM utilizaram no dia " +dia ,
                                querys.getPRamDia(dia,r.execReader(hostName)) );
                        chart2.runChart("10 processos que maior percentagem de RAM utilizaram no dia " +dia ,
                                querys.getPRamDia(dia,r.execReader(hostName)) );

                        break;

                    case 4:
                        System.out.println("Insira o IP do host no formato tcp/udp ," +
                                "para o localhost/161 digite default");

                        String host4 = sc.next();
                        if(!host4.equals("default")) {
                            hostName = host4;
                        }
                        Set<String> set = new HashSet<>();
                        set = querys.getPIDSzero(r.execReader(hostName));

                        System.out.println("\n");
                        for (String s : set){
                            System.out.println(s);
                        }

                    break;

                    default:
                        System.out.println("Opção inválida");
                }
            }
        }

    }

