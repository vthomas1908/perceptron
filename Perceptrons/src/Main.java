import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	final static int DIGITS = 10;
	final static int NODES = 785;
	//  50 training epochs
	// plus 1 epoch to get accuracy before training
	final static int EPOCHS = 51;

	// variables

	// each learning rate will keep track of its own data
	static Data[] rates = { new Data(.001), new Data(.01), new Data(.1) };

	// Array lists to hold the data samples
	static List<double[]> training = new ArrayList<double[]>();
	static List<double[]> testing = new ArrayList<double[]>();

	// determines if dataset is training/testing
	static boolean is_training = true;

	// holds current weights for each perceptron
	static double[][] p_weights = new double[DIGITS][NODES];

	// holds current dot product for each perceptron
	static double[] dp = new double[DIGITS];

	public static void main(String[] args) throws IOException {

		System.out.println("filling training array");

		// open the training file, get samples to add to the training array
		// list, and close the file when all samples collected
		FileHandle train_file = new FileHandle("mnist_train.csv");
		train_file.open_file();
		while (train_file.sample_available()) {
			training.add(train_file.get_sample().clone());
		}
		train_file.close_file();

		// also fill the testing sample array list
		System.out.println("filling testing array");
		FileHandle test_file = new FileHandle("mnist_test.csv");
		test_file.open_file();
		while (test_file.sample_available()) {
			testing.add(test_file.get_sample().clone());
		}
		test_file.close_file();

		// for each learning rate:
		for (int learn_rate = 0; learn_rate < rates.length; learn_rate++) {
			System.out.println("\nstarting learning rate: "
					+ rates[learn_rate].get_learn_rate() + "\n");

			// randomly set initial random weights for each perceptron [-.5, .5]
			for (int j = 0; j < DIGITS; j++) {
				for (int k = 0; k < NODES; k++) {
					Random rand = new Random();
					p_weights[j][k] = rand.nextDouble() - .5;
				}
			}

			// for each epoch
			for (int epoch = 0; epoch < EPOCHS; epoch++) {
				System.out.println("\nstarting epoch: " + epoch + "\n");

				// set to training state
				is_training = true;

				// this will calculate the dot product of each sample
				// calculate new weights (based on is_training and epoch
				// since epoch 0 does not get weight update)
				// get accuracy data
				if(epoch > 0){
					update_weights(training, learn_rate);
				}
				update_accuracy(training, epoch, learn_rate);

				// set to testing state
				is_training = false;

				// calculate dot product of each sample
				// no weight updates for testing
				// get accuracy data
				// calcuate confusion matrix on final epoch
				update_accuracy(testing, epoch, learn_rate);

				// shuffle training data for next epoch
				shuffleList(training);

			}// end for each epoch

			// give data total number of training and testing samples
			is_training = true;
			rates[learn_rate].set_total_count(is_training, training.size());
			rates[learn_rate].set_total_count(!is_training, testing.size());

			// calculate final accuracy for the learning rate
			rates[learn_rate].fianlize_data();
		}

		for (int learn_rate = 0; learn_rate < rates.length; learn_rate++) {
			// print accuracy data and confusion matrix for each learn rate
			System.out.println(rates[learn_rate].accuracy_to_string());
		}

	}

	// This is the function that will do all computations
	// Dot product, weight updates, accuracy data updates, confusion matrix
	private static void update_weights(List<double[]> dataset, int index) {
		for (double[] sample : dataset) {
			// get an analyzer
			Analyzer x = new Analyzer();

			// at each perceptron
			for (int perceptron = 0; perceptron < DIGITS; perceptron++) {

				// get the dot product for the current perceptron
				// given current perceptron weights and current sample nodes
				dp[perceptron] = x.dot_product(p_weights[perceptron], sample);

				// for training samples only, update weights for the perceptron
				if (is_training) {
					int y = x.y_output(dp[perceptron]);
					int t = x.t_output(perceptron, sample);

					// if y = t, then no need to update weights
					// since got correct outcome
					// if y != t, then must update the weights for
					// the perceptron
					if ((t != y)) {
						p_weights[perceptron] = x.update_weights(
								p_weights[perceptron], sample,
								rates[index].get_learn_rate(), (t - y)).clone();
					}
				}

			} // end for each perceptron
		}
	}

	// This is the function that will do all computations
	// Dot product, weight updates, accuracy data updates, confusion matrix
	private static void update_accuracy(List<double[]> dataset, int epoch,
			int index) {
		for (double[] sample : dataset) {
			// get an analyzer
			Analyzer x = new Analyzer();

			// at each perceptron
			for (int perceptron = 0; perceptron < DIGITS; perceptron++) {

				// get the dot product for the current perceptron
				// given current perceptron weights and current sample nodes
				dp[perceptron] = x.dot_product(p_weights[perceptron], sample);

			} // end for each perceptron

			// after finishig the sample, get accuracy for this epoch
			x.get_accuracy(rates[index], epoch, dp, (int) sample[0],
					is_training);
		}
	}

	// found this shuffle list from
	// http://www.vogella.com/tutorials/JavaAlgorithmsShuffle/article.html
	// this will shuffle the training data per homework instruction
	public static void shuffleList(List<double[]> a) {
		int n = a.size();
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < n; i++) {
			int change = i + random.nextInt(n - i);
			swap(a, i, change);
		}
	}

	// The other part of the shuffle function found from
	// http://www.vogella.com/tutorials/JavaAlgorithmsShuffle/article.html
	private static void swap(List<double[]> a, int i, int change) {
		double[] helper = a.get(i);
		a.set(i, a.get(change));
		a.set(change, helper);
	}

}
