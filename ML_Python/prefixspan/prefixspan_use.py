#pip install pyprefixspan
#from pyprefixspan import pyprefixspan
from prefixspan_util import pyprefixspan

data = [
    'a c d',
    'a b c',
    'c b a',
    'a a b'
]

p = pyprefixspan(data)
p.run()
print p.out
 