public class Data {
	// variables
	int DIGITS = 10;
	int EPOCHS = 51; // include extra for initial 0th accuracy

	double learn_rate;

	// train set
	double train_samples = 0;
	double[] train_correct = new double[EPOCHS];
	double[] train_accuracy = new double[EPOCHS];

	// test set
	double test_samples = 0;
	double[] test_correct = new double[EPOCHS];
	double[] test_accuracy = new double[EPOCHS];
	int[][] test_matrix = new int[DIGITS][DIGITS];

	public Data(double learn_rate) {
		this.learn_rate = learn_rate;
	}

	//retrun learning rate for this instance
	public double get_learn_rate() {
		return learn_rate;
	}

	//increment the number of correct predictions
	//for given dataset at given epoch
	public void increment_correct_count(boolean train, int epoch) {
		if (train) {
			train_correct[epoch]++;
		} else {
			test_correct[epoch]++;
		}
	}

	//set the total sample count for the given dataset
	public void set_total_count(boolean train, int count) {
		if (train) {
			train_samples = count;
		} else {
			test_samples = count;
		}
	}

	//update the confusion matrix - increment the value 
	//in the cell at the given indices 
	public void matrix_update(int actual, int prediction) {
		test_matrix[actual][prediction]++;
	}

	//calculate the % accuracy at each epoch for both datasets
	public void fianlize_data() {
		for (int epoch = 0; epoch < EPOCHS; epoch++) {
			train_accuracy[epoch] = train_correct[epoch] / train_samples;
			test_accuracy[epoch] = test_correct[epoch] / test_samples;
		}
	}

	//print out the results in a nice, easy to read format
	public String accuracy_to_string() {
		String string = "";
		String train_string = "{ ";
		String test_string = "{ ";
		String matrix_string = "";
		for (int epoch = 0; epoch < EPOCHS; epoch++) {
			train_string += train_accuracy[epoch] + " ";
			test_string += test_accuracy[epoch] + " ";
		}
		train_string += " }";
		test_string += " }";

		for (int p = 0; p < DIGITS; p++) {
			matrix_string += "    { ";
			for (int p2 = 0; p2 < DIGITS; p2++) {
				matrix_string += test_matrix[p][p2] + "  ";
			}
			matrix_string += "} \n";
		}

		string = "Learning Rate " + learn_rate + ":\n    Training acuracy: "
				+ train_string + "\n\n    Testing accuracy: " + test_string
				+ "\n\n    Confusion Matrix: \n" + matrix_string + "\n\n";

		return string;
	}

}
