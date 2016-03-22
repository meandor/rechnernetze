# Rechnernetze

[![Build Status](https://travis-ci.org/meandor/rechnernetze.svg?branch=master)](https://travis-ci.org/meandor/rechnernetze)

This repository contains the solutions for the exercises for the lecture Rechnernetze SS16 at the HAW.

## Exercise no. 1

Build a messaging ticker with a gui. The gui has to contain an input field for the content and a selection for specified
content types. All messages have to be displayed in order.
 
Threads should be used to generate random messages with a random selection of the content types and random intervals
in which they are posted. A queue should be used to sort the incoming messages. The thread count should be specified
during startup.

### Usage

The first program argument specifies the number of threads that should be started.