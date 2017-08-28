

public class Analyzer {
	int NODES = 785;
	int EPOCHS = 51;//include extra for 0th epoch
	
	
	public Analyzer(){
		
	}
	
	//compute dot product of the sample nodes of each sample
	// and the weights of the perceptron 
	public double dot_product(double[] weights, double[] sample){
		//temp dot product variable
		double dp = 0;
		
		//dot product calculation
		//excluding the first node which is just the "class"
			for(int node = 0; node < NODES; node++){
				//ignore first value of sample array since that is the sample value
				dp += weights[node] * sample[node + 1];
			}
			
		return dp;
	}
	
	//calculate the actual output of the perceptron (the y value)
	//recall y = 1 if dot product for perceptron in > 0
	public int y_output(double dp){
		if(dp > 0){
			return 1;
		}else{
			return 0;
		}
	}
	
	//calculate the target output of the perceptron (the t value)
	//t = 1 if the sample value is equal to the perceptron's learning number
	public int t_output(int num, double[] sample){
		if(sample[0] == num){
			return 1;
		}else{
			return 0;
		}
	}
	
	//this function is only called when appropriate (y != t)
	//this will update the weights for a perceptron
	//weights do not get updated when target output matches perceptron output (y==t)
	public double[] update_weights(double[] weights, double[] sample, double learn_rate, int diff){
		
		for(int node = 0; node < NODES; node++){
			//don't use the first element of sample since that is the "class"
			weights[node] += learn_rate * diff * sample[node + 1];
		}
		
		return weights;
	}
	
	//calculate the accuracy for this sample on the perceptrons
	public void get_accuracy(Data data, int epoch, double[] dp, int sample_val, boolean train){
		//vars for prediction and highest dot product value
		int prediction = 0;
		double high = dp[0];
		
		//prediction for the sample is the class with highest value
		for(int i = 1; i < dp.length; i++){
			if(dp[i] > high){
				high = dp[i];
				prediction = i;
			}
		}
		
		//if the prediction matches the actual class, the prediction is correct
		//class is the "sample_val"
		//update the number of correct responses for this learn rate
		if(prediction == sample_val){
			data.increment_correct_count(train, epoch);
		}
		
		//update confusion matrix 
		//only after all epochs have run and only for testing dataset
		if((epoch == EPOCHS - 1) & !train){
			data.matrix_update(sample_val, prediction);
		}
	}

}