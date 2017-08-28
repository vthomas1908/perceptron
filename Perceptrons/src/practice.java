
public class practice {
	
	
	
	// Here is where computation begins
	/*
			// for each learning rate:
			for (int i = 0; i < rates.length; i++) {

				// set initial random weights for each perceptron [-.5, .5]
				for (int j = 0; j < DIGITS; j++) {
					for (int k = 0; k < NODES; k++) {
						Random rand = new Random();
						p_weights[j][k] = rand.nextDouble() - .5;
					}
				}

				// for 50 epochs (cycles through all samples in file each epoch)
				for (int epoch = 0; epoch < EPOCHS; epoch++) {

					// for each file name (train, test) -->similar procedure,
					// differences noted
					for (int name = 0; name < file_names.length; name++) {

						// declare a new instance of the Analyzer class which will
						// perform analysis on the input file
						Analyzer x = new Analyzer(file_names[name]);

						// open the file
						x.open_file();

						// loop on all samples in the file
						while (x.sample_available()) {

							// get the sample from the file
							x.get_sample();

							// the following computations must be done for each
							// perceptron

							// for each perceptron use the current sample data
							for (int perceptron = 0; perceptron < DIGITS; perceptron++) {

								// compute dot product on weights and sample data
								dp[perceptron] = x
										.dot_product(p_weights[perceptron]);

								// only do the below if training. Test file will
								// not update weights since training is done
								if (is_training) {

									// compute actual (y) and target (t) output for
									// perceptron
									y = x.y_output(dp[perceptron]);
									t = x.t_output(perceptron);

									// if y = t, then no need to update weights
									// since got correct outcome
									// if y != t, then must update the weights for
									// the perceptron
									// don't update on 0th epoch so we can record
									// initial accuracy
									if ((t != y) && (epoch > 0)) {
										p_weights[perceptron] = x.update_weights(
												p_weights[perceptron],
												rates[i].learn_rate, (t - y));
									}
								}

							}// end for each perceptron

							// compute the accuracy for this sample
							x.get_accuracy(rates[i], epoch, dp, is_training);

						}// end for each sample in file

						// after going through all samples in the file
						// give data total number of samples and close file
						rates[i].set_total_count(is_training, x.get_sample_count());
						x.close_file();

					}// end for file name (test/train)

					// update is_training as file will switch
					if (is_training) {
						is_training = false;
					} else {
						is_training = true;
					}

				}// end for each epoch

			}// end learning rate for loop
	*/

}
