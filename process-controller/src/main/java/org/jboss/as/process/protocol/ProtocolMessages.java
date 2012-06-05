/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.process.protocol;

import org.jboss.logging.Cause;
import org.jboss.logging.Message;
import org.jboss.logging.MessageBundle;
import org.jboss.logging.Messages;

import java.io.EOFException;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.net.ConnectException;
import java.net.URI;

/**
 * Date: 21.07.2011
 *
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@MessageBundle(projectCode = "JBAS")
public interface ProtocolMessages {
    /**
     * The default messages.
     */
    ProtocolMessages MESSAGES = Messages.getBundle(ProtocolMessages.class);

    /**
     * Creates an exception indicating a connection is already connected.
     *
     * @return an {@link IllegalStateException} for the error.
     */
    @Message(value = "Already connected")
    IllegalStateException alreadyConnected();

    /**
     * Creates an exception indicating the channel and receiver have already been started.
     *
     * @return an {@link IllegalStateException} for the error.
     */
    @Message(value = "Channel and receiver already started")
    IllegalStateException alreadyStarted();

    /**
     * Creates an exception indicating that both connect timeout and the connect timeout property cannot be used
     * together.
     *
     * @return an {@link IllegalArgumentException} for the error.
     */
    @Message(value = "Can't use both a connect timeout and a connect timeout property")
    IllegalArgumentException cannotSpecifyMultipleTimeouts();

    /**
     * Creates an exception indicating the uriScheme cannot be set with the endpoint.
     *
     * @return an {@link IllegalArgumentException} for the error.
     */
    @Message(value = "Can't set uriScheme with specified endpoint")
    IllegalArgumentException cannotSetUriScheme();

    /**
     * Creates an exception indicating a connection could not be made.
     *
     * @param uri             the URI attempted to connect.
     * @param timeout         the timeout of the connection.
     * @param timeoutProperty the timeout property name.
     *
     * @return a {@link java.net.ConnectException} for the error.
     */
    @Message(value = "Could not connect to %s in %sms. Make sure the server is running and/or consider setting a longer timeout by setting -D%s=<timeout in ms>.")
    ConnectException couldNotConnect(URI uri, long timeout, String timeoutProperty);

    /**
     * Creates an exception indicating a connection could not be made.
     *
     * @param connectionTimeout the connection timeout
     *
     * @return a {@link java.net.ConnectException} for the error.
     */
    @Message(value = "Could not connect to remote server at %1$s within %1$sms")
    ConnectException couldNotConnect(long connectionTimeout);

    /**
     * Creates an exception indicating the executor is needed when not endpoint is specified.
     *
     * @return an {@link IllegalArgumentException} for the error.
     */
    @Message(value = "Need an executor when endpoint is not specified")
    IllegalArgumentException executorNeeded();

    /**
     * Creates an exception indicating the executor is not needed when an endpoint is specified.
     *
     * @return an {@link IllegalArgumentException} for the error.
     */
    @Message(value = "Don't need an executor when specified endpoint")
    IllegalArgumentException executorUnneeded();

    /**
     * Creates an exception indicating a failure to create the server thread.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Failed to create server thread")
    IOException failedToCreateServerThread();

    /**
     * Creates an exception indicating a failure to read the object.
     *
     * @param cause the cause of the error.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Failed to read object")
    IOException failedToReadObject(@Cause Throwable cause);

    /**
     * Creates an exception indicating a failure to write management response headers.
     *
     * @param cause the cause of the error.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Failed to write management response headers")
    IOException failedToWriteManagementResponseHeaders(@Cause Throwable cause);

    /**
     * Creates an exception indicating an invalid byte.
     *
     * @return an {@link java.io.UTFDataFormatException} for the error.
     */
    @Message(value = "Invalid byte")
    UTFDataFormatException invalidByte();

    /**
     * Creates an exception indicating an invalid byte.
     *
     * @param c the character.
     * @param i the raw integer.
     *
     * @return an {@link java.io.UTFDataFormatException} for the error.
     */
    @Message(value = "Invalid byte:%s(%d)")
    UTFDataFormatException invalidByte(char c, int i);

    /**
     * Creates an exception indicating an invalid byte token was found.
     *
     * @param expected the expected value.
     * @param actual   the actual value.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Invalid byte token.  Expecting '%s' received '%s'")
    IOException invalidByteToken(int expected, byte actual);

    /**
     * Creates an exception indicating the an invalid command byte was read.
     *
     * @param commandByte the command byte read.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Invalid command byte read: %s")
    IOException invalidCommandByte(int commandByte);

    /**
     * Creates an exception indicating the signature is invalid.
     *
     * @param signature the invalid signature.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Invalid signature [%s]")
    IOException invalidSignature(String signature);

    /**
     * Creates an exception indicating an invalid start chunk was found.
     *
     * @param chunk the start chunk.
     *
     * @return an {@code IOException} for the error.
     */
    @Message(value = "Invalid start chunk start [%s]")
    IOException invalidStartChunk(int chunk);

    /**
     * Creates an exception indicating the type is invalid.
     *
     * @param type the invalid type.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Invalid type: %s")
    IOException invalidType(String type);

    /**
     * Creates an exception indicating the provided type was invalid.
     *
     * @param validType1   the first valid type.
     * @param validType2   the second valid type.
     * @param providedType the type provided.
     *
     * @return an {@link IllegalArgumentException} for the error.
     */
    @Message(value = "Type is neither %s or %s: %s")
    IllegalArgumentException invalidType(String validType1, String validType2, byte providedType);

    /**
     * Creates an exception indicating only the {@code url} is a valid URL.
     *
     * @param url the valid url.
     *
     * @return an {@link IllegalArgumentException} for the error.
     */
    @Message(value = "Only '%s' is a valid url")
    IllegalArgumentException invalidUrl(String url);

    /**
     * Creates an exception indicating there is no operation handler set.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "No operation handler set")
    IOException operationHandlerNotSet();

    /**
     * Creates an exception indicating there is no connection.
     *
     * @return an {@link IllegalStateException} for the error.
     */
    @Message(value = "Not connected")
    IllegalStateException notConnected();

    /**
     * Creates an exception indicating the {@code varName} is {@code null}.
     *
     * @param varName the variable name.
     *
     * @return an {@link IllegalArgumentException} for the error.
     */
    @Message(value = "%s is null")
    IllegalArgumentException nullVar(String varName);

    /**
     * Creates an exception indicating the {@code parameterName1} and {@code parameterName2} are {@code null}.
     *
     * @param parameterName1 the parameter name.
     * @param parameterName2 the parameter name.
     *
     * @return an {@link IllegalArgumentException} for the error.
     */
    @Message(value = "%s and %s are null")
    IllegalArgumentException nullParameters(String parameterName1, String parameterName2);

    /**
     * Creates an exception indicating the number of bytes read.
     *
     * @param bytesRead the number of bytes read.
     *
     * @return an {@link java.io.EOFException} for the error.
     */
    @Message(value = "Read %d bytes.")
    EOFException readBytes(int bytesRead);

//    /**
//     * Creates an exception indicating there was no request handler found with the id in the operation handler.
//     *
//     * @param id               the id of the request handler.
//     * @param operationHandler the operation handler the id was not found in.
//     *
//     * @return an {@link java.io.IOException} for the error.
//     */
//    @Message(value = "No request handler found with id %s in operation handler %s")
//    IOException requestHandlerIdNotFound(byte id, ManagementOperationHandler operationHandler);

    /**
     * Creates an exception indicating the response handler has already been registered for the request.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Response handler already registered for request")
    IOException responseHandlerAlreadyRegistered();

    /**
     * Creates an exception indicating the response handler id was not found for the request.
     *
     * @param id the id.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message("No response handler for request %s")
    IOException responseHandlerNotFound(int id);

    /**
     * Creates an exception indicating a problem happened executing on the server.
     *
     * @param errorMessage the error message.
     *
     * @return an {@link java.io.IOException} for the error
     */
    @Message(value = "A problem happened executing on the server: %s")
    IOException serverError(String errorMessage);

    /**
     * Creates an exception indicating the stream is closed.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Stream closed")
    IOException streamClosed();

    /**
     * Creates an exception indicating the thread creation was refused.
     *
     * @return an {@link IllegalStateException} for the error.
     */
    @Message(value = "Thread creation was refused")
    IllegalStateException threadCreationRefused();

    /**
     * Creates an exception indicating the an unexpected end of stream was detected.
     *
     * @return an {@link java.io.EOFException} for the error.
     */
    @Message(value = "Unexpected end of stream")
    EOFException unexpectedEndOfStream();

    /**
     * Creates an exception indicating the scheme does not match the URI.
     *
     * @param scheme the scheme.
     * @param uri    the uri.
     *
     * @return an {@link IllegalArgumentException} for the error.
     */
    @Message(value = "Scheme %s does not match uri %s")
    IllegalArgumentException unmatchedScheme(String scheme, URI uri);

    /**
     * Creates an exception indicating the write channel is closed.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Write channel closed")
    IOException writeChannelClosed();

    /**
     * Creates an exception indicating the writes have already been shutdown.
     *
     * @return an {@link java.io.IOException} for the error.
     */
    @Message(value = "Writes are already shut down")
    IOException writesAlreadyShutdown();
}
