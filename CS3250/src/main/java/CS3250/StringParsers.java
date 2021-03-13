package CS3250;

public class StringParsers {

    public String[] parseConnectionString(String s){
        String[] arr = new String[3];
        s+=" ";
        String buffer = "";
        String[] information = new String[3];
        int place = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' '){
                information[place] = buffer;
                buffer = "";
                place++;
            }
            else{
                buffer+= s.charAt(i);
            }
        }
        arr[0] = information[0];
        arr[1] = information[1];
        arr[2] = information[2];
        return arr;
    }
    
}
