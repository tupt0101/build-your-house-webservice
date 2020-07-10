/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.utils;

import java.io.Serializable;

import static tupt.constants.CharacterConstant.*;

/**
 *
 * @author sherl
 */
public class CharacterUtil implements Serializable {
	public static boolean isStartVariableName(char character) {
		return Character.isLetter(character) || character == COLON || character == UNDERSCORE;
	}
	
	public static boolean isInsideVariableName(char character) {
		return Character.isLetterOrDigit(character) || character == COLON || character == UNDERSCORE;
	}
}
