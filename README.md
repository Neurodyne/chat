# Welcome to Chat !
Chat is a Chisel Assertions Toolkit. It aims to implement a comprehensive assertions package for [Chisel Hardware Construction Language.](https://github.com/freechipsproject/chisel3/)

Chat preserves System Verilog Assertions 1800-2009 syntax, implementing assertions as immutable entities in Scala and Chisel. 

## SVA basics
Conceptually, assertion is a small declarative statement, which checks some specific behavior of signals in a single ( immediate assertions) or across multiple ( temporal assertions ) clock cycles . A single assertion describes a specific behavior of a signal group, so checking multiple design properties requires different types of assertions. <br/>

Assertions don't change during simulation or formal verification, so updating an assertion requires recompilation of the whole design.

Let's consider a simple example of SVA assertion. <br/>
Spec: For each read request, read acknowledge or read abort should be high in 1 to 5 clock cycles.

### Property 1
```Verilog
property readPerf;
  rdReq |-> [1:5] ( rdAck || rdAbort )
endproperty 

a_perf: assert property ( readPerf );
c_perf: cover  property ( readPerf );
```
### Property 2
```Verilog
property readPerf;
  rdReq |=> [1:5] ( rdAck || rdAbort )
endproperty 

a_perf: assert property ( readPerf );
c_perf: cover  property ( readPerf );
```
Each assertion consists of two parts: an **antecedent** and a **consequent**, delimited by an **implication** operator. An antecedent is what is on the left of the implication and a consequent is what's on the right.

There two kinds of **implication** operators, namely **immediate |->** and **deffered |=>** implications. Immediate implication starts to evaluate the consequent on the same clock cycle, as the antecedent. A deffered implication operator starts to evaluate the consequent on the following clock cycle after the antecedent has triggered.
Clearly, that a deffered operator can be expressed as follows:
```Verilog
|=> === |->nexttime[1] 
or
|=> === |->##
```

## Chisel implementation details

1. Chat needs to support multiple backends, including [FIRRTL](https://github.com/freechipsproject/firrtl) and Verilator
2. Since Verilator doesn't support temporal assertions, Chat needs to be implemented in Scala and use Scala **assert** statement on every evaluation attempt.
3. Chat should be implemented in abstract level for simulation and formal verification. Assertion synthesis may be implemented as well with [Midas](https://github.com/ucb-bar/midas), but currently is not a priority
4. All assertions should be evaluated on the Rising Edge. Chisel introduces a concept of Clock() to Scala, and the Rising Edge may be derived as follows:
```Scala
def risingedge(x: Bool) = x && !RegNext(x)
```
5. Chat must support both immediate and deffered implication operators.
