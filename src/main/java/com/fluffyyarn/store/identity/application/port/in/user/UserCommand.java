package com.fluffyyarn.store.identity.application.port.in.user;

public interface UserCommand {
  String getUsername();
  String getPassword();
}
