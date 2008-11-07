"""MoinMoin - MathTran

Usage:
    [[MathTran(e^{i \pi} = -1)]]

@copyright: 2007 Timothy Head
@license: GNU GPL v2
"""

def execute(macro, formula):
   return macro.formatter.image(alt=r"tex:"+ formula)
