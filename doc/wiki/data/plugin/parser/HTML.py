# -*- coding: iso-8859-1 -*-
"""
  MoinMoin - HTML Source Parser
  
  @copyright: 2006 by Johannes Hoerburger <jh@underground8.com>
  @copyright: 09.2006 by Eduard Baun <edy@edy-b.de>
  @copyright: 2007 by Daniel Horth <dan@dev.flickr.ath.cx>
  @license: GNU GPL.
  
  (Modified Eduard Baun's PHP parser)

"""

from MoinMoin.util.ParserBase import ParserBase

Dependencies = []

class Parser(ParserBase):

    parsername = "ColorisedHTML"
    extensions = ['.htm','.html']
    Dependencies = []

    def setupRules(self):
        ParserBase.setupRules(self)
        self.addRulePair("Comment","<--","-->/")
        self.addRulePair("String",r"(?<!\\)\"",r"(?<!\\)\"")
        self.addRulePair("String",r"(?<!\\)\'",r"(?<!\\)\'")
        self.addRule("Char",r"'\\.'|'[^\\]'") 
        self.addRule("Number",r"[0-9](\.[0-9]*)?(eE[+-][0-9])?[flFLdD]?|0[xX][0-9a-fA-F]+[Ll]?")
        self.addRule("ID","[a-zA-Z_][0-9a-zA-Z_]*")
        self.addRule("SPChar",r"[~!%^&*()+=|\[\]:;,.<>/?{}-]")
