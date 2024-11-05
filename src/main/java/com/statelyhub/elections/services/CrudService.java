/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.services;

import com.stately.modules.jpa2.CrudController;
import com.stately.modules.jpa2.Enviroment;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author Edwin
 */
@Stateless
public class CrudService extends CrudController
{
    
  @PersistenceContext
  private EntityManager em;
  
  
  
  
  @PostConstruct
  private void initComplete()
  {
      setEnviroment(Enviroment.JAVA_EE);
      setEm(em);
  }
    
}
