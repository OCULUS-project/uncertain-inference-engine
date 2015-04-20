/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.kbtest.contract;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static pl.kbtest.contract.Config.GLOBAL_SPLIT_REGEX;

/**
 *
 * @author Kamil
 */
    public class SetFactFactory {

        private SetFactFactory(){}
        
        public static SetFact getInstance(final String head, final String body,final GrfIrf grfIrf,
                final BigDecimal wParamter, final boolean axiom, boolean conj){
            boolean negate = false;
            String[] b = body.split(GLOBAL_SPLIT_REGEX);
            if(b.length > 1){
                if(b[1].trim().equals("!")){
                    negate = true;
                }
            }
            Set<String> set = new HashSet(Arrays.asList(b));
            return new DefaultSetFact(head,set,grfIrf,wParamter,axiom,negate,conj);
        }
        public static SetFact getInstance(final String head, final String body,final GrfIrf grfIrf, boolean conj){
            boolean negate = false;
            String[] b = body.split(GLOBAL_SPLIT_REGEX);
            if(b.length > 1){
                if(b[1].trim().equals("!")){
                    negate = true;
                }
            }
            Set<String> set = new HashSet(Arrays.asList(body.split(GLOBAL_SPLIT_REGEX)));
            return new DefaultSetFact(head,set,grfIrf,BigDecimal.ONE,false,negate,conj);
        }
        
        public static SetFact getInstance(final String head, final Set<String> bodySet, final GrfIrf grfIrf, final BigDecimal w, boolean negate, boolean conj){
            
            return new DefaultSetFact(head,bodySet,grfIrf,w,false, negate, conj);
        }
        
        public static SetFact getInstance(final String fact, final GrfIrf grfIrf, boolean conj){
            boolean negate = false;
            String[] parts = fact.split(GLOBAL_SPLIT_REGEX);
              if(parts.length > 1){
                if(parts[1].trim().equals("!")){
                    negate = true;
                }
            }
            return new DefaultSetFact(parts[0],
                     new HashSet<String>(Arrays.asList(Arrays.copyOfRange(parts,1,parts.length))),
                     grfIrf,BigDecimal.ONE,false,negate,conj);
        }
        


    }
