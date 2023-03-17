/*a. Statement of purpose:
This program will take an input text file of account numbers and a user input of an account number.
It will then sort the account numbers from lowest to highest and search through the account numbers to determine if
the user input is contained in the array of account numbers.
If it is, the program will return true, if not, it will return false.

b. Author and date:
Siddhartha Narker
2/26/23

c. Description of the program's input and output:
Input - A list of account numbers, user input of an account number
Output - Unsorted list of account numbers, and success or failure depending on if the user input is located in the given
array.

d. Description of how to use the program:
The program will ask you to input a 7-digit account number. It will let you know if your entered account number is in
the list of account numbers provided as inputFile.txt.

e. Assumptions such as the type of data expected
The input file should contain a list of integer numbers. The user input should contain only integers.

f. Statement of exceptions; that is, what could go wrong
If the file is not found correctly, the program will throw a FileNotFoundException.

g. Brief description of the major classes
quickSort - sorts the list in blocks around a pivot value
swap - swaps two numbers in an array. Used in the quicksort method
binary search - searches for a value in a sorted list by taking the median and comparing the search value to the median,
passing it recursively back until a value is found or the array has been completely searched through.
*/

import java.util.Scanner;
import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        /*Read file input from file called inputFile.txt. We had to put in the pathway because we kept getting a
        FileNotFound Exception when it couldn't automatically locate the file.*/
        File inputFile = new File("C:\\Users\\sinar\\IdeaProjects\\HW3\\src\\inputFile.txt");
        Scanner sc = new Scanner(inputFile);
        //Collect user input bank account number and store in userAccount.
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter your 7-digit account number: ");
        int userAccount = userInput.nextInt();
        //Created ArrayList so that I could discern the length of the list before adding into the int array
        ArrayList<Integer> accounts = new ArrayList<>();

        //added elements to array
        while(sc.hasNext()){
            accounts.add(sc.nextInt());
        }
        //Created array with size of the size of ArrayList
        int[] accountsArray = new int[accounts.size()];

        //added elements from the ArrayList into the array
        for(int i = 0; i < accounts.size(); i++){
            accountsArray[i] = accounts.get(i);
        }

        //printed out the Unsorted array
        System.out.println("Unsorted Accounts List: ");
        for(int a = 0; a < accounts.size(); a++){
            System.out.println(accountsArray[a]);
        }

        //sorted the array by passing it to the quickSort method we created. Passed an index so that the pointers in the
        //method would have a value.
        quickSort(accountsArray, 0, accountsArray.length - 1);

        //Printed out sorted array
        System.out.println("Sorted Accounts List: ");
        for(int a = 0; a < accounts.size(); a++){
            System.out.println(accountsArray[a]);
        }

        //passed array to the binary search method to fin user inputted account number, alon with indexes that serve the
        //same purpose as they did in the quickSort algorithm
        binarySearch(accountsArray, userAccount, 0, accountsArray.length - 1);

    }

    public static void quickSort(int[] a, int low, int high) {
        //because the method is recursive it needs a way to stop recalling the method. If there are no elements in the
        // sorting block it will stop recalling.
        if(low >= high) {
            return;
        }

        //We made our pivot the last element in the array and sorted the two blocks around that value.
        int pivotPoint = a[high];

        //passed low and high indexes
        int leftIndex = low;
        int rightIndex = high;

        //created while loop to add another catch, when the loop breaks the values will swap
        while(leftIndex < rightIndex){
            //left index increments until it finds a value greater than the pivot
            while(a[leftIndex] <= pivotPoint && leftIndex < rightIndex){
                leftIndex++;
            }
            //right index decrements until it finds a value lesser than the pivot
            while(a[rightIndex] >= pivotPoint && leftIndex < rightIndex){
                rightIndex--;
            }

            //swap method swaps the two values
            swapValues(a, leftIndex, rightIndex);
        }

        //swaps the left index that is higher than the pivot with the pivot
        swapValues(a, leftIndex, high);

        //repasses two subarrays to quickSort method, one with all the values lower than pivotPoint and one with all the
        //values higher than pivotPoint
        quickSort(a, low, leftIndex-1);
        quickSort(a, leftIndex+1, high);
    }
    public static void swapValues(int[] array, int indexA, int indexB){
        //swap value method stores one value in a temporary object and sets the objects equal to eachother and then the
        //second object equal to the holder object
        int hold = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = hold;
    }

    public static void binarySearch(int[] a, int searchValue, int low, int high){
        //same catch as in the quickSort algorithm. If the method goes through the array without finding the user value
        //it returns the println statement.
        if (low > high) {
            System.out.println("False: Invalid Account Number. Please try again and enter a valid 7-digit account number.");
        }
        else{
            //finds the middle value of the array
            int splitValue = (low + high) / 2;

            //if the value matches the splitValue the method returns the following print statement
            if (searchValue == a[splitValue]){
                System.out.println("Success! Your account number is at index: " + splitValue);
            }
            //because the array is sorted, if the value is less than the split value, the value must be in the first
            //half of the array
            else if (searchValue < a[splitValue]){
                //sends the first half of the array through the search method recursively
                binarySearch(a, searchValue, low, splitValue - 1);
            }
            //if it's not in the first half, it's in the second, so it sends the second half through the array
            //recursively
            else {
                binarySearch(a, searchValue, splitValue + 1, high);
            }
        }
    }
}