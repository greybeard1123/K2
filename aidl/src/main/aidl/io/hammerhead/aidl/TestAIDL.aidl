// TestAIDL.aidl
package io.hammerhead.aidl;

interface TestAIDL {
    oneway void sendMessage(String message);
    String getMessage();
}
