import java.util.Scanner;
public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n,q, timer = 0, maxProcessIndex = 0;
        float avgWait = 0, avgTurnT = 0;
        System.out.println("Round Robin");
        // the user enter the quantum time
        System.out.print("\nEnter the time quantum : ");
        q = sc.nextInt();
        // Take number of process
        System.out.print("\nEnter the number of processes : ");
        n = sc.nextInt();
        //initialize arrays
        int[] arrival = new int[n];
        int[] burst = new int[n];
        int[] wait = new int[n];
        int[] turn = new int[n];
        int[] queue = new int[n];
        int[] temp_burst = new int[n];
        boolean[] complete = new boolean[n];
        // Take the arrival and burst time from the user
        for(int i = 0; i < n; i++) {
            System.out.print("\nEnter the arrival time of the process "+(i+1)+" : ");
            arrival[i] = sc.nextInt();
            System.out.print("\nEnter the burst time of the process "+(i+1)+" : ");
            burst[i] = sc.nextInt();
            temp_burst[i] = burst[i];
        }
        //Initializing the queue and complete array
        for(int i = 0; i < n; i++){
            complete[i] = false;
            queue[i] = 0;
        }
        // incremental the time
        while(timer < arrival[0])
            timer++;
        queue[0] = 1;
        // check process is completed or not
        while(true){
            boolean flag = true;
            for(int i = 0; i < n; i++){
                if(temp_burst[i] != 0){
                    flag = false;
                    break;
                }
            }
            if(flag)
                break;
            for(int i = 0; (i < n) && (queue[i] != 0); i++){
                int ctr = 0;
                while((ctr < q) && (temp_burst[queue[0]-1] > 0)){
                    temp_burst[queue[0]-1] -= 1;
                    timer += 1;
                    ctr++;
                    //Updating the ready queue until all the processes arrive
                    checkNewArrival(timer, arrival, n, maxProcessIndex, queue);
                }
                if((temp_burst[queue[0]-1] == 0) && (!complete[queue[0] - 1])){
                    turn[queue[0]-1] = timer;        //turn currently stores exit times
                    complete[queue[0]-1] = true;
                }
                //checks whether CPU is idle
                boolean idle = true;
                if(queue[n-1] == 0){
                    for(int k = 0; k < n && queue[k] != 0; k++){
                        if (!complete[queue[k] - 1]) {
                            idle = false;
                            break;
                        }
                    }
                }
                else
                    idle = false;

                if(idle){
                    timer++;
                    checkNewArrival(timer, arrival, n, maxProcessIndex, queue);
                }
                //Maintaining the entries of processes after each preemption in the ready Queue
                queueMaintenance(queue,n);
            }
        }
        for(int i = 0; i < n; i++){
            turn[i] = turn[i] - arrival[i];
            wait[i] = turn[i] - burst[i];
        }
        System.out.print("""

                ProgramNo. \tArrival Time\tBurst Time\tWait Time\tTurnAround Time
                """);
        for(int i = 0; i < n; i++){
            System.out.print(i+1+"\t\t\t\t"+arrival[i]+"\t\t\t\t"+burst[i]
                    +"\t\t\t"+wait[i]+"\t\t\t"+turn[i]+ "\n");
        }
        for(int i =0; i< n; i++){
            avgWait += wait[i];
            avgTurnT += turn[i];
        }
        System.out.print("\nAverage wait time : "+(avgWait/n)
                +"\nAverage Turn Around Time : "+(avgTurnT/n));
    }
    public static void queueInundation(int[] queue, int n, int maxProcessIndex){
        int zeroIndex = -1;
        for(int i = 0; i < n; i++){
            if(queue[i] == 0){
                zeroIndex = i;
                break;
            }
        }
        if(zeroIndex == -1)
            return;
        queue[zeroIndex] = maxProcessIndex + 1;
    }
    public static void checkNewArrival(int timer, int[] arrival, int n, int maxProcessIndex, int[] queue){
        if(timer <= arrival[n-1]){
            boolean newArrival = false;
            for(int j = (maxProcessIndex+1); j < n; j++){
                if(arrival[j] <= timer){
                    if(maxProcessIndex < j){
                        maxProcessIndex = j;
                        newArrival = true;
                    }
                }
            }
            if(newArrival)
                //adds the index of the arriving process(if any)
                queueInundation(queue, n, maxProcessIndex);
        }
    }
    public static void queueMaintenance(int[] queue, int n){

        for(int i = 0; (i < n-1) && (queue[i+1] != 0) ; i++){
            int temp = queue[i];
            queue[i] = queue[i+1];
            queue[i+1] = temp;
        }
    }
}