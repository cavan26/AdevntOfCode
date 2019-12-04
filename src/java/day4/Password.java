package day4;


public class Password {
    public static void main(String[] args) {
        Password password = new Password();
        int count = 0;
        System.out.println(password.twoConsecutiveDigits(1111122));
        for (int i= 138307; i <= 654504; i++){
            if (password.twoConsecutiveDigits(i) && password.neverDecrease(i)){
                count+=1;
            }
        }
        System.out.println(count);
    }

    public boolean twoConsecutiveDigits(int number){
        String str = Integer.toString(number);
        char prev = str.charAt(0);
        int count = 0;
        for (int i = 1; i < str.length(); i++){
            char curr = str.charAt(i);
            if (curr == prev) {
                count +=1;
            } else if (count == 1) {
                return true;
            } else {
                count = 0;
            }
            prev = curr;
        }
        if (count==1){
            return true;
        }
        return false;
    }

    public boolean neverDecrease(int number) {
        String str = Integer.toString(number);
        int prev = Character.getNumericValue(str.charAt(0));
        for (int i = 1; i < str.length(); i++){
           int curr =  Character.getNumericValue(str.charAt(i));
           if (curr< prev){
               return false;
           }
           prev = curr;
        }
        return true;
    }
}
