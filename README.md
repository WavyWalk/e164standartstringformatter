# string to E.164 standard compliant number formatter with info guessing for any phone number for any country.

More detailed info is given for german numbers, but you can provide a config for any country. 

# German numbers formatter

This formatter expects that you provide it with a german number in arbitrary format.

Basically it will analyze validness of sdn allowed by Germany's numbering rules, it's lengths etc, skip unallowed identifiers
and prefix it with cc if needed.

As a bonus it will provide you with information about the number type.
If input number complies with Germany's rules it'd be formatted to E.164's one,
if it can't be mapped, it'd be not formatted and show you the reason.
e.g.:
```
${numberToTest}:
    ${programOutput}

0235234:
    49235234
    Geographic Area Code
    Altena Westfalen

+49 (0) 2352351:
    492352351
    Geographic Area Code
    Altena Westfalen

002352351
    002352351 does not comply with Germany numbering plan

49023523
    Invalid. number's length is not allowed by numbering plan //here for the that code 
    Geographic Area Code
    Altena Westfalen
```

# International

This formatter expects that you provide any number of any country, and will try to map it to one of the countries and 
format it accordingly.

It does so by analysing possible country code, exit code and trunk combinations.

Number will be treated and formatted to E.164 if it can be treated as international - e.g.
if any valid combination of cc, exit codes, and trunks exists for any
country in the world (respecting multiple codes for one country).

In case of code / exit code colllisions (overlappings like cc 1 with same exits for example) it will as result get you the
list of countries that comply with provided number and format input respecting their specified rules.

If it's not possible to infer the country from number, it will be considered as non compliant and not formatted, showing
the reason.

No numbering plans are implemented for countries. So result number is not validated against specific numbering plan. It
just respects the size prescribed by standard and that it should include valid cc (but will do so if you provide a csv config).
```
e.g.:
00313h4i5r2e4m5e2462:
Netherlands - 313452452462

6223 53 (5)23*51:
Indonesia - 62235352351
Indonesia - Indosat - 62235352351
Indonesia  - Telkom - 62235352351
Indonesia - Bakrie Telecom - 62235352351

008 62 0 5668:
Indonesia - Indosat - 625668

7264627373462245236:
7264627373462245236 is non compliant with E.164, §6.2.1 does not allow such length, exceeds 15 digits
```

# How to use

Clone, build, run main.kt

I've bashed some terminal application where you choose formatter and enter numbers, but you can basically uncomment some 
lines to be able to navigate in code with ide.

# notes on implementaion

For German formatter - numbering plan is parsed to a tree, and app just simply walks till dead end (meaning fail) or 
node containing result and does some additional stuff like skipping trunks etc.

With international formatter there are two trees - one with exit code + cc, and other as cc leading to a endpoint countries. 
Two of combinations of identifiers are for the reason that in international context (where number can belong to any country) 
it is otherwise would be quite hard to find out to which country it belongs in order to construct a compliant number 
(though it's doable if all countries numbering plans were to be implemented, though it wouldn't consistently yield/guess result).

There's like plenty refactoring necessary but, and that's more of an experiment.