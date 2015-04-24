import java.util.Arrays;

public class Sortierung
{

	public static boolean isSorted(int[] array)// determines whether array is ascending
	{
		return isSorted(array, 0, array.length);
	}

	public static boolean isSorted(int[] array, int start, int length)// determines whether array is ascending
	{
		if (start >= length)
			return true;

		for (int i = start + 1; i < length; i++)
			if (array[i] < array[i - 1])// if previous item is bigger as the current, the array is not sorted
				return false;
		return true;
	}

	public static void insertionSort(int[] array)
	{
		for (int j = 1; j < array.length; j++)
		{
			int key = array[j];// item to insert
			int i = j - 1;
			while (i >= 0 && array[i] > key)// move to the left until the left hand item is smaller than the key or the beginning of the array is reached
			{
				array[i + 1] = array[i];
				i--;
			}
			array[i + 1] = key;// insert item to its right position so that the array from 0 to j is correctly sorted
			assert isSorted(array, 0, j);
		}

		assert isSorted(array);
	}

	public static void mergeSort(int[] array)
	{
		mergeSort(array, 0, array.length-1);
	}

	public static void mergeSort(int[] array, int start, int length)
	{
		if (length-start>0)//array is already sorted if start==length
		{
			int mid = (int)Math.floor((start + length) / 2);//split array

			mergeSort(array, start, mid);
			mergeSort(array, mid+1, length);
			merge(array, start, mid, length);
			assert isSorted(array, start, length);
		}
	}
	
	static void merge(int[] array, int start, int mid, int end)//merges two sorted sets into one sorted set
	{
		int[] result = new int[end - start+1];

		int leftCounter = start;
		int rightCounter = mid+1;
		int index = 0;// current position in the merged (result) array

		assert isSorted(array,leftCounter,mid+1);
		assert isSorted(array,rightCounter,end+1);
		
		while (leftCounter <= mid && rightCounter <= end)// as long as there are items in the left and the right part of our merge array
		{
			if (array[leftCounter] > array[rightCounter])// write the smallest of both items into the result array
				result[index++] = array[rightCounter++];
			else
				result[index++] = array[leftCounter++];

			assert isSorted(result,0,index);
		}
		
		//add all remaining items from one of the parts (since the other is empty)
		while(leftCounter <= mid)
		{
			result[index++] = array[leftCounter++];
		}
		while(rightCounter <= end)
		{
			result[index++] = array[rightCounter++];
		}
		
		assert isSorted(result);
		
		//write result back into the original array
		for(int i=0;i<=end-start;i++)
			array[start+i]=result[i];
	}
}
