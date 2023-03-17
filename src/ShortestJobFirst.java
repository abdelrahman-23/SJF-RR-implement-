import java.util.*;
public class ShortestJobFirst
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Shortest Job First");
        System.out.print("\nEnter no of process: ");
        int n = sc.nextInt();
        int[] id = new int[n];
        int[] arrival = new int[n];
        int[] burst = new int[n];
        int[] complete = new int[n];
        int[] turnaround = new int[n];
        int[] waiting = new int[n];
        int[] flag = new int[n];  // checks process is completed or not
        int st = 0, total = 0;
        float avgwaiting = 0, avgtunaround = 0;
        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter process " + (i + 1) + " arrival time: ");
            arrival[i] = sc.nextInt();
            System.out.print("\nEnter process " + (i + 1) + " burst time: ");
            burst[i] = sc.nextInt();
            id[i] = i+1;
            flag[i] = 0;
        }
        while(true)
        {
            int c=n, min=999;
            if (total == n)
                break;
            for (int i=0; i<n; i++)
            {

                if ((arrival[i] <= st) && (flag[i] == 0) && (burst[i]<min))
                {
                    min=burst[i];
                    c=i;
                }
            }
            if (c==n)
                st++;
            else
            {
                complete[c]=st+burst[c];
                st+=burst[c];
                turnaround[c]=complete[c]-arrival[c];
                waiting[c]=turnaround[c]-burst[c];
                flag[c]=1;
                total++;
            }
        }
        System.out.println("id arrival burst  complete turn"+"\t" +"waiting");
        for(int i=0;i<n;i++)
        {
            avgwaiting+= waiting[i];
            avgtunaround+= turnaround[i];
            System.out.println(id[i]+"\t\t"+arrival[i]+"\t\t"+burst[i]+"\t\t"+complete[i]+"\t\t"+turnaround[i]+"\t\t"+waiting[i]);
        }
        System.out.println ("\nAverage tat is "+ (avgtunaround/n));
        System.out.println ("Average wt is "+ (avgwaiting/n));
    }
}