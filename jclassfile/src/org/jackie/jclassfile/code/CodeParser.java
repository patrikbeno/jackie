package org.jackie.jclassfile.code;

import static org.jackie.jclassfile.code.InstructionFactoryManager.instructionFactoryManager;
import org.jackie.jclassfile.constantpool.ConstantPool;
import static org.jackie.utils.Assert.expected;
import org.jackie.utils.Log;
import org.jackie.utils.XDataInput;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class CodeParser {

	protected XDataInput input;
	protected int length;
	protected ConstantPool constantPool;
	protected Instruction last;
	protected List<CodeResolver> resolvers;

	public CodeParser(XDataInput input, int length, ConstantPool constantPool) {
		this.input = input;
		this.length = length;
		this.constantPool = constantPool;
		this.resolvers = new ArrayList<CodeResolver>(length/3);
	}

	public XDataInput input() {
		return input;
	}

	public int length() {
		return length;
	}

	public ConstantPool constantPool() {
		return constantPool;
	}

	public Instruction last() {
		return last;
	}

	public void append(Instruction instruction) {
		if (last != null) {
			last = last.append(instruction);
		} else {
			last = instruction;
		}
	}

	public void addResolver(CodeResolver resolver) {
		resolvers.add(resolver);
	}

	public Instruction parse() {

		int toread = length;
		Instruction insn = null;

		while (toread > 0) {
			insn = read();
			toread -= insn.size();
		}

		expected(0, toread, "length != read");

		resolve();

		return insn.head();
	}

	Instruction read() {
		int opcode = input.readUnsignedByte();

		InstructionFactory f = instructionFactoryManager().getFactory(opcode);
		Instruction insn = f.loadInstruction(opcode, this);

		Log.debug("Loaded instruction :%s", insn);

		return insn;
	}

	protected void resolve() {
		for (CodeResolver resolver : resolvers) {
			resolver.execute();
		}
	}

}
