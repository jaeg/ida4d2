package ida.Utilities;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;

class ParserDemo2 {

	/** Usage: ParserDemo2 [[grammar] textFile] */
	public static void main(String[] args) throws IOException {
		String grammar = args.length > 0 ? args[0] : "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
		String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
		LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

		Iterable<List<? extends HasWord>> sentences;
		if (args.length > 1) {
			DocumentPreprocessor dp = new DocumentPreprocessor(args[1]);
			List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
			for (List<HasWord> sentence : dp) {
				tmp.add(sentence);
			}
			sentences = tmp;
		} else {
			// Showing tokenization and parsing in code a couple of different
			// ways.
			String[] sent = { "Why", "do", "you", "hate", "the", "rain"};
			List<HasWord> sentence = new ArrayList<HasWord>();
			for (String word : sent) {
				sentence.add(new Word(word));
			}
			String sent2 = ("I like riding my bike.");
			Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sent2));
			List<? extends HasWord> sentence2 = toke.tokenize();
			List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
			tmp.add(sentence);
//			tmp.add(sentence2);
			sentences = tmp;
		}

		for (List<? extends HasWord> sentence : sentences) {
			Tree parse = lp.apply(sentence);
			parse.pennPrint();
			System.out.println();
			System.out.println(parse.taggedYield());
			System.out.println();

			GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
			Collection tdl = gs.typedDependenciesCCprocessed(true);
			System.out.println(tdl);
			System.out.println();
		}
//
//		String sent3 = "I like writing my bike!";
//		lp.apply(sent3).pennPrint();
	}

}