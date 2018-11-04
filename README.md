# Welcome to Chat !
Chat is a Chisel Assertions Toolkit. It aims to implement an assertions package for [Chisel Hardware Construction Language.](https://github.com/freechipsproject/chisel3/)

Chat preserves SVA 1800-2009 syntax, implementing assertions as immutable entities in Scala and Chisel. 

## SVA implementation
Let's consider a simple example of SVA assertion.
Spec: For each read request, read acknowledge or read abort should go high in 1 to 5 clock cycles.

```System Verilog
property readPerf;
  rdReq |-> [1:5] ( rdAck || rdAbort )
endproperty 

a_perf: assert property ( readPerf );
c_perf: cover  property ( readPerf );
```
### SVA basics
Assertions in System Verilog are small declarative statements, which are *expected*

### Implication Operators
|-> is the immediate implication operator 
|=> is the defferd implication operator, which check

## Chisel implementation details
1. Chat needs to support multiple backends, including [FIRRTL](https://github.com/freechipsproject/firrtl) and Verilator
2. Since Verilator doesn't support temporal assertions, Chat needs to be implemented in Scala and use Scala and Verilator assert statement on every evaluation attempt.
3. Chat should be implemented in abstract level for simulation and formal verification. Assertions synthesis may be implemented as well with [Midas](https://github.com/ucb-bar/midas), but currently is not a priority
4. All assertions should be evaluated on the Rising Edge. Chisel introduces a concept of Clock() to Scala, and the Rising Edge may be derived as follows:
```Scala
def risingedge(x: Bool) = x && !RegNext(x)
```
5. Chat must support standard SVA implication operators, namely
|-> as an immediate implication operator, e.g x |-> y means that
